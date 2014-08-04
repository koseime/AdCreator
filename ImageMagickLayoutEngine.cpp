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
#include <stdio.h>
#include <sstream>
#include <string>
#include <cstring>
#include <assert.h>
#include <stdlib.h>
#include <string.h>

#include <wand/magick_wand.h>

#include "AdComponentsMessages.pb.h"
#include "LayoutEngineManager.h"
#include <Magick++.h>

using namespace std;
using namespace Magick;


ImageMagickLayoutEngine::ImageMagickLayoutEngine() {
	MagickCoreGenesis(NULL,MagickTrue);
}

ImageMagickLayoutEngine::~ImageMagickLayoutEngine() {
	// TODO Auto-generated destructor stub
}

int ImageMagickLayoutEngine::importResources(const string &path) {
	return layoutEngineManager.importImagesAndLayouts(path);
}

int ImageMagickLayoutEngine::create(const char* product_image_file, const char* ad_text, const char* output) {
	// Load file content into byte array
	std::ifstream fl(product_image_file);
	fl.seekg( 0, std::ios::end );
	size_t len = fl.tellg();
	char *product_image = new char[len];
	fl.seekg(0, std::ios::beg);
	fl.read(product_image, len);
	fl.close();

	com::kosei::proto::AdComponents temp;
	temp.set_productjpg(product_image, len);
	int retVal = create(&temp, ad_text, output);

	delete[] product_image;
	return retVal;
}

void ImageMagickLayoutEngine::createFromLayouts(const com::kosei::proto::AdComponents* product_info, vector<pair<string, Blob> > &generatedAds) {
	vector<LayoutEngineManager::AdLayoutEntry> adLayouts;
	layoutEngineManager.getAdLayouts(adLayouts);
	generatedAds.reserve(adLayouts.size());
	for (int i = 0; i < adLayouts.size(); i++) {
		// Create product image
		const std::string &image_product_string = product_info->productjpg();
		Blob blob(image_product_string.c_str(), image_product_string.length());
		Image product_image(blob);

		// Create Background image
		Blob *background_blob = layoutEngineManager.getImageBlob(adLayouts[i].getBackgroundFilename());
		if (background_blob == NULL) {
			cerr << "Error loading background image " + adLayouts[i].getBackgroundFilename();
			continue;
		}
		Image background_image(*background_blob);

		// Scale the product image
		Geometry sz = product_image.size();
		int scle = (int)( ((double) 44/(double)sz.height() ) * 100);
		std::ostringstream s;
		s << scle ;
		product_image.scale( Geometry(s.str()) );

		// Test overlay/composite
		background_image.composite(product_image, 3, 3,OverCompositeOp);

		// Write output
		generatedAds.push_back(pair<string, Blob>());
		int last = generatedAds.size() - 1;
		generatedAds[last].first = adLayouts[i].getId();
		background_image.write(&generatedAds[last].second);
	}
}

char *ImageMagickLayoutEngine::createToBuffer(const com::kosei::proto::AdComponents* product_info, const char* ad_text, size_t *length) {
	const std::string &image_product_string = product_info->productjpg();
	Blob blob(image_product_string.c_str(), image_product_string.length());
	Image product_image( blob );

	///Create Background image
	Image background_image(*(layoutEngineManager.getImageBlob("320x50Border.png")));

	//Scale the product image
	Geometry sz = product_image.size();
	int scle = (int)( ((double) 44/(double)sz.height() ) * 100);
	std::ostringstream s;
	s << scle ;
	product_image.scale( Geometry(s.str()) );

	//Test overlay/composite
	background_image.composite(product_image, 3, 3,OverCompositeOp);

	Blob output_blob;
	background_image.write(&output_blob);
	*length = output_blob.length();
	char *output_buffer = new char[output_blob.length()];
	memcpy(output_buffer, (char *)output_blob.data(), output_blob.length());
	return output_buffer;
}

double computeScale(double size, double height, double width) {
	return size / max(height, width);
}

void sscaleImage(Image *image, int size) {
	Geometry imageSize = image->size();
	double scale = computeScale((double)size, (double)imageSize.height(), (double)imageSize.width());
	int newWidth = floor((double)imageSize.width() * scale + 0.5);
	int newHeight = floor((double)imageSize.height() * scale + 0.5);

	image->scale(Geometry(newWidth, newHeight));
	image->extent(Geometry(size, size), CenterGravity);
}

int ImageMagickLayoutEngine::create(const com::kosei::proto::AdComponents* product_info, const char* ad_text, const char* output) {
	// Create Product image
	const std::string &image_product_string = product_info->productjpg();
	Blob blob(image_product_string.c_str(), image_product_string.length());
	Image productImage(blob);

	// Create Background image
	Image backgroundImage("320x50", "white");
	//backgroundImage.read("images/320x50Border.png");

	// Create Logo image
	Image logoImage;
	logoImage.read("images/a.png");

	// Scale and composite the product image
	sscaleImage(&productImage, 42);
	backgroundImage.composite(productImage, 4, 4, OverCompositeOp);

	// Scale and composite the logo image
	sscaleImage(&logoImage, 50);
	backgroundImage.composite(logoImage, NorthEastGravity, OverCompositeOp);

	// Add title and description
	/*MagickCore::MagickWand *mw = NULL;
	size_t width,height;

	MagickCore::MagickWandGenesis();
	mw = MagickCore::NewMagickWand();

	MagickCore::MagickSetSize(mw,200,30);
	MagickCore::MagickSetFont(mw,"@/Users/chantat/kosei/AdCreator/6457.ttf");
	MagickCore::MagickSetOption(mw,"fill","red");
	MagickCore::MagickSetOption(mw,"background","white");
	MagickCore::MagickSetOption(mw,"weight","Bold");

	MagickCore::MagickSetGravity(mw,CenterGravity);
	MagickCore::MagickReadImage(mw,"caption:jelly");
	    	MagickCore::MagickWriteImage(mw,"caption2.gif");
		    if(mw)DestroyMagickWand(mw);
*/

	//backgroundImage.boxColor("green");
	list<Drawable> textDrawList;
	textDrawList.push_back(DrawableFont("@/Users/chantat/kosei/AdCreator/6457.ttf", NormalStyle, 600, NormalStretch));
	textDrawList.push_back(DrawablePointSize(20));
	textDrawList.push_back(DrawableText(60, 23,"Headline goes here"));
	backgroundImage.draw(textDrawList);

	textDrawList.clear();
	textDrawList.push_back(DrawableFont("@/Users/chantat/kosei/AdCreator/6457.ttf", NormalStyle, 400, NormalStretch));
	textDrawList.push_back(DrawablePointSize(12));
	textDrawList.push_back(DrawableText(60, 42,ad_text));
	backgroundImage.draw(textDrawList);
	//backgroundImage.annotate(string(ad_text), Geometry(200, 20, 60, 28));

	backgroundImage.write(output);

	return 0;
}




