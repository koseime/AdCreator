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

#include "LayoutEngine.h"
#include <cairo/cairo.h>
#include <iostream>
#include <fstream>
#include <stdio.h>

#include <stdlib.h>
#include <string.h>

#include "AdComponentsMessages.pb.h"


LayoutEngine::LayoutEngine() {
	// TODO Auto-generated constructor stub

}

LayoutEngine::~LayoutEngine() {
	// TODO Auto-generated destructor stub
}

int LayoutEngine::create(const char* product_image_file, const char* ad_text, const char* output) {
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

int LayoutEngine::create(const com::kosei::proto::AdComponents* product_info, const char* ad_text, const char* output) {
	cairo_surface_t *input_img_surface  = NULL;

	/*
	if (strlen(image_product_file) >= 4 && strcmp(image_product_file + strlen(image_product_file) - 4, ".jpg") == 0) {
		input_img_surface = jpegUtil.loadJpg(image_product_file);
	} else {
		input_img_surface  = cairo_image_surface_create_from_png(image_product_file);
	}
	 */
	
	const std::string &image_product_string = product_info->productjpg();
	char *image_product = new char [image_product_string.length()];
	std::memcpy(image_product, image_product_string.c_str(), image_product_string.length());
	
	input_img_surface = jpegUtil.loadJpgFromMemory(image_product, image_product_string.length());

	delete[] image_product;

	cairo_surface_t *border_surface = cairo_image_surface_create_from_png("images/320x50Border.png");

	//Create the background image
	cairo_surface_t *img = cairo_image_surface_create(CAIRO_FORMAT_ARGB32, 320, 50);

	//Create the cairo context
	cairo_t *cr = cairo_create(img);

	//Product image manipulation
	cairo_t *product_cr;
	cairo_surface_t *product_surface;

	int width = cairo_image_surface_get_width (input_img_surface);
	int height = cairo_image_surface_get_height (input_img_surface);

	//Scale the product image
	product_surface = cairo_image_surface_create(CAIRO_FORMAT_ARGB32, width, height);
	double scle = (double) 44/(double)height;
	product_cr = cairo_create(product_surface);
	cairo_scale (product_cr,scle,scle);
	cairo_set_source_surface(product_cr, input_img_surface, 0, 0);
	cairo_paint(product_cr);

	//Paint the border image
	cairo_set_source_surface(cr, border_surface, 0, 0);
	cairo_paint(cr);

	/* Draw some text */
	cairo_select_font_face (cr, "sans", CAIRO_FONT_SLANT_NORMAL, CAIRO_FONT_WEIGHT_NORMAL);
	cairo_set_font_size (cr, 10);
	cairo_set_source_rgb(cr, 0, 0, 0);
	cairo_move_to (cr, 80, 20);
	cairo_show_text (cr, ad_text);

	//paint the scaled product image
	cairo_set_source_surface(cr, product_surface, 3, 3);
	cairo_paint(cr);

	//cleanup the product
	cairo_surface_destroy(product_surface);
	cairo_surface_destroy(input_img_surface);
	cairo_destroy(product_cr);

	//Destroy the cairo context and/or flush the destination image
	cairo_surface_flush(img);
	cairo_destroy(cr);

	//And write the results into a new file
	cairo_surface_write_to_png(img, output);

	//Be tidy and collect your trash
	cairo_surface_destroy(img);
	cairo_surface_destroy(border_surface);
	return 0;
}




