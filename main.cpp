//
//  main.cpp
//  AdCreator
//
//  Created by Lance Riedel on 7/7/14.
//  Copyright (c) 2014 Lance Riedel. All rights reserved.
//

#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <cairo/cairo.h>

//./gl-cairo-simple vacuum.png "We think you would like this vacuum" out.png

int main(int argc, char *argv[])
{
	printf ("Num args: %d", argc);


	if (argc!=4) {
		while(argc--)
		printf("%s\n", *argv++);

		printf("\nUsage: <product image> <product description> <output>");
		exit(1);
	}
	//Load a few images from files
	cairo_surface_t *input_img_surface  = cairo_image_surface_create_from_png(argv[1]);
	cairo_surface_t *border_surface = cairo_image_surface_create_from_png("320x50Border.png");
	char* ad_text = argv[2];
	char* output = argv[3];


	//Create the background image
	cairo_surface_t *img = cairo_image_surface_create(CAIRO_FORMAT_ARGB32, 320, 50);

	//Create the cairo context
	cairo_t *cr = cairo_create(img);


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
