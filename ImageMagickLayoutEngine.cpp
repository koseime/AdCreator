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
	generatedAds.reserve(layoutEngineManager.adLayouts.size());
	for (int i = 0; i < layoutEngineManager.adLayouts.size(); i++) {
		// Create product image
		const std::string &image_product_string = product_info->productjpg();
		Blob blob(image_product_string.c_str(), image_product_string.length());
		Image product_image(blob);

		// Create Background image
		Image background_image(*(layoutEngineManager.getImageBlob(layoutEngineManager.adLayouts[i].background_filename)));

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
		generatedAds[last].first = layoutEngineManager.adLayouts[i].id;
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

int ImageMagickLayoutEngine::create(const com::kosei::proto::AdComponents* product_info, const char* ad_text, const char* output) {

	const std::string &image_product_string = product_info->productjpg();
	Blob blob(image_product_string.c_str(), image_product_string.length());
	Image product_image( blob );

	///Create Background image
	Image background_image;
	background_image.read("images/320x50Border.png");



	//Scale the product image
	Geometry sz = product_image.size();
	int scle = (int)( ((double) 44/(double)sz.height() ) * 100);
	std::ostringstream s;
	s << scle ;
	product_image.scale( Geometry(s.str()) );

	//Test overlay/composite
	background_image.composite(product_image, 3, 3,OverCompositeOp);


//
//	/* Draw some text */
//	cairo_select_font_face (cr, "sans", CAIRO_FONT_SLANT_NORMAL, CAIRO_FONT_WEIGHT_NORMAL);
//	cairo_set_font_size (cr, 10);
//	cairo_set_source_rgb(cr, 0, 0, 0);
//	cairo_move_to (cr, 80, 20);
//	cairo_show_text (cr, ad_text);

	background_image.write(output);

	return 0;
}




