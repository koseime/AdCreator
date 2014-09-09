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
#include <cassert>

#include "Magick++.h"

#include "LayoutEngineManager.h"
#include "AdLayoutEntry.h"

using namespace std;
using namespace MagickCore;

ImageMagickLayoutEngine::ImageMagickLayoutEngine() {
	MagickWandGenesis();
}

ImageMagickLayoutEngine::~ImageMagickLayoutEngine() {
	if (drawingWand) { drawingWand = DestroyDrawingWand(drawingWand); }
	if (pixelWand) { pixelWand = DestroyPixelWand(pixelWand); }
	MagickWandTerminus();
}

int ImageMagickLayoutEngine::importResources(const string &path) {
	pixelWand = NewPixelWand();
	drawingWand = NewDrawingWand();
	isResourceImported = true;
	return layoutEngineManager.importImagesAndLayouts(path);
}

void ImageMagickLayoutEngine::createAllLayouts(const string &productImage, const string &title,
		const string &copy, vector<pair<string, string> > *generatedAds) {
	generatedAds->clear();
	generatedAds->reserve(layoutEngineManager.getAdLayoutsSize());
	for (int i = 0; i < layoutEngineManager.getAdLayoutsSize(); i++) {
		AdLayoutEntry adLayoutEntry = layoutEngineManager.getAdLayouts(i);

		// Generate Ad
		generatedAds->push_back(pair<string, string>());
		int last = generatedAds->size() - 1;
		generatedAds->at(last).first = adLayoutEntry.name;
		create(productImage, adLayoutEntry, title, copy, adLayoutEntry.backgroundColor, &generatedAds->at(last).second);
	}
}

int ImageMagickLayoutEngine::create(const string &productImage, const AdLayoutEntry &adLayoutEntry,
		const string &title, const string &copy, const string &backgroundColor, string *outputBlob) {
	return create(productImage, layoutEngineManager.getImageBlob(adLayoutEntry.background.fileName),
			layoutEngineManager.getImageBlob(adLayoutEntry.logo.fileName), adLayoutEntry, title, copy, backgroundColor,
			outputBlob);
}

int ImageMagickLayoutEngine::create(const string &product_image_file, const string &title,
		const string &copy, const string &backgroundColor, const string &output_file) {
	// Load file content into string

	ifstream fs(product_image_file.c_str());
	string productBlob((istreambuf_iterator<char>(fs)), istreambuf_iterator<char>());

	int retVal;

	string ad;
	retVal = create(productBlob, layoutEngineManager.getAdLayouts(0), title, copy, backgroundColor, &ad);

	ofstream outputStream(output_file.c_str());
	outputStream.write(ad.data(), ad.size());
	outputStream.close();

	return retVal;
}


void ImageMagickLayoutEngine::drawText(MagickWand *backgroundMagickWand, const AdLayoutEntry::TextEntry &textEntry,
		const string &text) {
	PixelSetColor(pixelWand, textEntry.fontColor.c_str());
	DrawSetFillColor(drawingWand, pixelWand);
	DrawSetFontSize(drawingWand, textEntry.fontSize);
	DrawSetFontWeight(drawingWand, textEntry.fontWeight);
	string type = "";
	if (textEntry.fontType.compare("normal")) {
		type = "-" + textEntry.fontType;
	}

    MagickWand *mw = NULL;
    MagickWandGenesis();
    mw = NewMagickWand();

    MagickSetSize(mw,210,20);
    if (textEntry.fontSize!=0) {
        MagickSetPointsize(mw, textEntry.fontSize);
    }
    MagickSetFont(mw,("@fonts/" + textEntry.fontName + type + ".ttf").c_str());
    MagickSetOption(mw,"fill",textEntry.fontColor.c_str());
    MagickSetOption(mw,"background","none");
    MagickSetGravity(mw,NorthWestGravity);



    DrawSetFont(drawingWand, ("@fonts/" + textEntry.fontName + type + ".ttf").c_str());

	if (text.empty()) {
	    MagickReadImage(mw,("caption:" + textEntry.defaultText).c_str());

		//MagickAnnotateImage(backgroundMagickWand, drawingWand, textEntry.pos_x, textEntry.pos_y, 0, textEntry.defaultText.c_str());
	} else {
		    MagickReadImage(mw,("caption:" + text).c_str());

		//MagickAnnotateImage(backgroundMagickWand, drawingWand, textEntry.pos_x, textEntry.pos_y, 0, text.c_str());
	}

	MagickCompositeImage(backgroundMagickWand, mw, OverCompositeOp,
    				textEntry.pos_x, textEntry.pos_y);
        //////////////
//	FormatMagickCaption(Image *,DrawInfo *,const MagickBooleanType,TypeMetric *,
//        char **);
//
//
//      MagickAnnotateImage(MagickWand *,const DrawingWand *,const double,
//         const double,const double,const char *),
//
//	FormatMagickCaption


	///////////
	// TODO: clear DrawingWand and check textbox is within the specified bounding box
}

void ImageMagickLayoutEngine::cropSquare(MagickWand *magickWand) {
    size_t width = (size_t)MagickGetImageWidth(magickWand);
	size_t height = (size_t)MagickGetImageHeight(magickWand);

	size_t min = std::min(height, width);

	//TODO: if ratio is too great, we may need to back fill with a color to get more square first
	//TODO: acutally detect the important parts of the picture

    MagickCropImage(magickWand,
        min,min,0,0);


}

void ImageMagickLayoutEngine::scaleAndExtendImage(MagickWand *backgroundMagickWand, MagickWand *magickWand,
		const AdLayoutEntry::ImageEntry &imageEntry) {
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

}

void ImageMagickLayoutEngine::createRoundedRectangleMask(MagickWand *maskMagickWand, int size_x, int size_y) {
	PixelSetColor(pixelWand, "none");
	MagickNewImage(maskMagickWand, size_x, size_y, pixelWand);
	PixelSetColor(pixelWand, "white");
	DrawSetFillColor(drawingWand, pixelWand);

	int rx = size_x / 8;
	int ry = size_y / 8;

	DrawRoundRectangle(drawingWand, 1, 1, size_x - 1, size_y - 1, rx, ry);
	MagickDrawImage(maskMagickWand, drawingWand);
}

int ImageMagickLayoutEngine::create(const string &productImage, const string &backgroundBlob,
		const string &logoBlob, const AdLayoutEntry &adLayoutEntry, const string &title,
		const string &copy, const string &backgroundColor, string *outputBlob) {
	// Initialization
	// TODO: create them once per object?
	assert(isResourceImported);
	MagickWand *backgroundMagickWand = NewMagickWand();
	MagickWand *productMagickWand = NewMagickWand();
	MagickWand *logoMagickWand = NewMagickWand();
	MagickWand *maskMagickWand = NewMagickWand();

	MagickSetCompressionQuality(backgroundMagickWand,100);
    MagickSetCompressionQuality(logoMagickWand,100);
    MagickSetCompressionQuality(productMagickWand,100);

	// Create Background image
	if (backgroundBlob.empty()) {
		PixelSetColor(pixelWand, backgroundColor.data());
		MagickNewImage(backgroundMagickWand, 320, 50, pixelWand);
	} else {
		MagickReadImageBlob(backgroundMagickWand, backgroundBlob.data(), backgroundBlob.size());
	}

	// Create, scale, round corners and composite product image
	if (adLayoutEntry.product.fileName.compare("valid") == 0) {
		MagickReadImageBlob(productMagickWand, productImage.data(), productImage.size());
        cropSquare(productMagickWand);
		scaleAndExtendImage(backgroundMagickWand, productMagickWand, adLayoutEntry.product);
		createRoundedRectangleMask(maskMagickWand, adLayoutEntry.product.size_x, adLayoutEntry.product.size_y);
		MagickCompositeImage(maskMagickWand, productMagickWand, SrcInCompositeOp, 0, 0);
		MagickCompositeImage(backgroundMagickWand, maskMagickWand, OverCompositeOp,
				adLayoutEntry.product.pos_x, adLayoutEntry.product.pos_y);
	}

	// Create, scale and composite the logo image
	if (!logoBlob.empty() && adLayoutEntry.logo.fileName.compare("invalid") != 0) {
		MagickReadImageBlob(logoMagickWand, logoBlob.data(), logoBlob.size());
		scaleAndExtendImage(backgroundMagickWand, logoMagickWand, adLayoutEntry.logo);
		MagickCompositeImage(backgroundMagickWand, logoMagickWand, OverCompositeOp,
				adLayoutEntry.logo.pos_x, adLayoutEntry.logo.pos_y);
	}

	// Add title and description
	drawText(backgroundMagickWand, adLayoutEntry.title, title);
	drawText(backgroundMagickWand, adLayoutEntry.description, copy);

	// Write image and clean up
	char *backgroundBytes;
	size_t backgroundBytesLen;
	// TODO: ask what format to use
	MagickSetImageFormat(backgroundMagickWand, "jpg");
	MagickSetCompressionQuality(backgroundMagickWand,100);
	MagickResetIterator(backgroundMagickWand);
	backgroundBytes = (char *)MagickGetImagesBlob(backgroundMagickWand, &backgroundBytesLen);
	*outputBlob = string(backgroundBytes, backgroundBytesLen);
	MagickRelinquishMemory(backgroundBytes);

	if (backgroundMagickWand) { backgroundMagickWand = DestroyMagickWand(backgroundMagickWand); }
	if (productMagickWand) { productMagickWand = DestroyMagickWand(productMagickWand); }
	if (logoMagickWand) { logoMagickWand = DestroyMagickWand(logoMagickWand); }
	if (maskMagickWand) { maskMagickWand = DestroyMagickWand(maskMagickWand); }

	return 0;
}





