/*
 * JpegUtil.cpp
 *
 *  Created on: Jul 10, 2014
 *      Author: lanceriedel
 */

#include "JpegUtil.h"

JpegUtil::JpegUtil() {
	// TODO Auto-generated constructor stub

}

JpegUtil::~JpegUtil() {
	// TODO Auto-generated destructor stub
}



cairo_surface_t * JpegUtil::decodeJPEGIntoSurface(jpeg_decompress_struct *info) {

	int width = info->output_width;
	int height = info->output_height;


	int stride = width * 4;
	cairo_status_t status;

	unsigned char *data = (unsigned char *) malloc(width * height * 4);
	if (!data) {
		jpeg_abort_decompress(info);
		jpeg_destroy_decompress(info);
		return NULL;
	}

	unsigned char *src = (unsigned char *) malloc(width * info->output_components);
	if (!src) {
		free(data);
		jpeg_abort_decompress(info);
		jpeg_destroy_decompress(info);
		return NULL;
	}

	for (int y = 0; y < height; ++y) {
		jpeg_read_scanlines(info, &src, 1);
		int *row = (int *)(data + stride * y);
		for (int x = 0; x < width; ++x) {
			int bx = 3 * x;
			int *pixel = row + x;
			*pixel = 255 << 24
					| src[bx + 0] << 16
					| src[bx + 1] << 8
					| src[bx + 2];
		}
	}

	cairo_surface_t * surface = cairo_image_surface_create_for_data(
			data
			, CAIRO_FORMAT_ARGB32
			, width
			, height
			, cairo_format_stride_for_width(CAIRO_FORMAT_ARGB32, width));

	jpeg_finish_decompress(info);
	jpeg_destroy_decompress(info);
	status = cairo_surface_status(surface);

	if (status) {
		free(data);
		free(src);
		return NULL;
	}

	free(src);


	return surface;
}

cairo_surface_t * JpegUtil::loadJpg(const char* Name){
	unsigned char a,r,g,b;
	int width, height;
	struct jpeg_decompress_struct cinfo;
	struct jpeg_error_mgr jerr;

	FILE * infile;    	/* source file */
	JSAMPARRAY pJpegBuffer;   	/* Output row buffer */
	int row_stride;   	/* physical row width in output buffer */
	if ((infile = fopen(Name, "rb")) == NULL)
	{
		fprintf(stderr, "can't open %s\n", Name);
		return 0;
	}
	cinfo.err = jpeg_std_error(&jerr);
	jpeg_create_decompress(&cinfo);
	jpeg_stdio_src(&cinfo, infile);
	(void) jpeg_read_header(&cinfo, TRUE);
	(void) jpeg_start_decompress(&cinfo);
	width = cinfo.output_width;
	height = cinfo.output_height;

	return decodeJPEGIntoSurface(&cinfo);
}

