/*
 * LayoutEngine.cpp
 *
 * TODO:
 * This is going to be like more like a gaming engine -- at least it will be a lot more
 * flexible for various layouts, cropping, background, etc
 *
 *  Created on: Jul 10, 2014
 *      Author: lanceriedel
 */

#include "ImageMagickLayoutEngine.h"
#include <iostream>
#include <fstream>
#include <vector>
#include <cstdio>
#include <sstream>
#include <string>
#include <cstring>
#include <cstdlib>

#include "magick/MagickCore.h"
#include "Magick++.h"

#include "AdComponentsMessages.pb.h"
#include "LayoutEngineManager.h"
#include "AdLayoutEntry.h"

using namespace std;
//using namespace Magick;
using namespace MagickCore;

ImageMagickLayoutEngine::ImageMagickLayoutEngine() {
	MagickCoreGenesis(NULL,MagickTrue);
}

ImageMagickLayoutEngine::~ImageMagickLayoutEngine() {
	// TODO Auto-generated destructor stub
}

int ImageMagickLayoutEngine::importResources(const string &path) {
	return layoutEngineManager.importImagesAndLayouts(path);
}

void ImageMagickLayoutEngine::createFromLayouts(const com::kosei::proto::AdComponents* product_info, vector<pair<string, Blob> > &generatedAds) {
	generatedAds.clear();
	generatedAds.reserve(layoutEngineManager.getAdLayoutsSize());
	for (int i = 0; i < layoutEngineManager.getAdLayoutsSize(); i++) {
		AdLayoutEntry adLayoutEntry = layoutEngineManager.getAdLayouts(i);

		// Create product image
		const std::string &image_product_string = product_info->productjpg();
		Blob productImage(image_product_string.c_str(), image_product_string.length());

		// Generate Ad
		generatedAds.push_back(pair<string, Blob>());
		int last = generatedAds.size() - 1;
		generatedAds[last].first = adLayoutEntry.name;
		create(&productImage, adLayoutEntry, product_info->title().c_str(), product_info->description().c_str(), &generatedAds[last].second);
	}
}

void ImageMagickLayoutEngine::drawText(MagickWand *backgroundMagickWand, DrawingWand *drawingWand, const AdLayoutEntry::TextEntry &textEntry, char *text) {
	DrawSetFontSize(drawingWand, textEntry.fontSize);
	DrawSetFontWeight(drawingWand, textEntry.fontWeight);
	DrawSetFont(drawingWand, textEntry.fontName.c_str());
	MagickAnnotateImage(backgroundMagickWand, drawingWand, textEntry.pos_x, textEntry.pos_y, 0, text);
	// TODO: clear DrawingWand and check textbox is within the specified bounding box
}

void ImageMagickLayoutEngine::scaleAndPlaceImage(MagickWand *backgroundMagickWand, MagickWand *magickWand, const AdLayoutEntry::ImageEntry &imageEntry) {
	double width = (double)MagickGetImageWidth(magickWand);
	double height = (double)MagickGetImageHeight(magickWand);
	// TODO: compute scale correctly
	double scale = ((double)imageEntry.size_x) / max(height, width);
	int newWidth = floor(width * scale + 0.5);
	int newHeight = floor(height * scale + 0.5);

	// TODO: select appropriate filter
	MagickResizeImage(magickWand, newWidth, newHeight, LanczosFilter, 1);
	// TODO: extend image correctly
	MagickExtentImage(magickWand, imageEntry.size_x, imageEntry.size_x, -(imageEntry.size_x-newWidth)/2,-(imageEntry.size_x-newHeight)/2);
	MagickCompositeImage(backgroundMagickWand, magickWand, OverCompositeOp, imageEntry.pos_x, imageEntry.pos_y);
}


int ImageMagickLayoutEngine::create(const char* product_image_file, const char* title, const char* copy, const char* output) {
	// Load file content into byte array
	std::ifstream fl(product_image_file);
	fl.seekg( 0, std::ios::end );
	size_t len = fl.tellg();
	char *product_image = new char[len];
	fl.seekg(0, std::ios::beg);
	fl.read(product_image, len);
	fl.close();

	Blob productBlob;
	productBlob.update(product_image, len);

	int retVal;
	AdLayoutEntry adLayoutEntry = AdLayoutEntry("Ad", "template_1", "null", "dd.jpg");

	Blob ad;
	retVal = create(&productBlob, adLayoutEntry, title, copy, &ad);

	ofstream outputStream(output);
	outputStream.write((const char *)ad.data(), ad.length());
	outputStream.close();

	delete[] product_image;
	return retVal;
}

int ImageMagickLayoutEngine::create(Blob *productImage, const AdLayoutEntry &adLayoutEntry,
		const char *title, const char *copy, Blob *outputBlob) {
	// Initialization
	// TODO: create them once per object?
	MagickWandGenesis();
	PixelWand *pixelWand = NewPixelWand();
	MagickWand *backgroundMagickWand = NewMagickWand();
	MagickWand *productMagickWand = NewMagickWand();
	MagickWand *logoMagickWand = NewMagickWand();
	DrawingWand *drawingWand = NewDrawingWand();
	PixelSetColor(pixelWand, "white");

	// Create Background image
	Blob *backgroundBlob = layoutEngineManager.getImageBlob(adLayoutEntry.background.fileName);
	if (backgroundBlob == NULL) {
		MagickNewImage(backgroundMagickWand, 320, 50, pixelWand);
	} else {
		MagickReadImageBlob(backgroundMagickWand, backgroundBlob->data(), backgroundBlob->length());
	}

	// Create, scale and composite product image
	if (adLayoutEntry.product.fileName.compare("valid") == 0) {
		MagickCore::MagickReadImageBlob(productMagickWand, productImage->data(), productImage->length());
		scaleAndPlaceImage(backgroundMagickWand, productMagickWand, adLayoutEntry.product);
	}

	// Create, scale and composite the logo image
	Blob *logoBlob = layoutEngineManager.getImageBlob(adLayoutEntry.logo.fileName);
	if (logoBlob != NULL) {
		MagickReadImageBlob(logoMagickWand, logoBlob->data(), logoBlob->length());
		scaleAndPlaceImage(backgroundMagickWand, logoMagickWand, adLayoutEntry.logo);
	}

	// Add title and description
	drawText(backgroundMagickWand, drawingWand, adLayoutEntry.title, (char *)title);
	drawText(backgroundMagickWand, drawingWand, adLayoutEntry.description, (char *)copy);

	// Write image and clean up
	char *backgroundBytes;
	size_t backgroundBytesLen;
	// TODO: ask what format to use
	MagickSetImageFormat(backgroundMagickWand, "jpg");
	MagickResetIterator(backgroundMagickWand);
	backgroundBytes = (char *)MagickGetImagesBlob(backgroundMagickWand, &backgroundBytesLen);
	outputBlob->update(backgroundBytes, backgroundBytesLen);
	MagickRelinquishMemory(backgroundBytes);

	if (backgroundMagickWand) { backgroundMagickWand = DestroyMagickWand(backgroundMagickWand); }
	if (productMagickWand) { productMagickWand = DestroyMagickWand(productMagickWand); }
	if (logoMagickWand) { logoMagickWand = DestroyMagickWand(logoMagickWand); }
	if (pixelWand) { pixelWand = DestroyPixelWand(pixelWand); }
	if (drawingWand) { drawingWand = DestroyDrawingWand(drawingWand); }
	MagickWandTerminus();

	return 0;
}





