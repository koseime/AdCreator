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

cairo_surface_t * JpegUtil::loadJpgFromFile(const char* Name) {
	unsigned char a,r,g,b;
	int width, height;
	struct jpeg_decompress_struct cinfo;
	struct jpeg_error_mgr jerr;

	FILE * infile;    	/* source file */
	JSAMPARRAY pJpegBuffer;   	/* Output row buffer */
	int row_stride;   	/* physical row width in output buffer */
	if ((infile = fopen(Name, "rb")) == NULL) {
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


cairo_surface_t * JpegUtil::loadJpgFromMemory(const char* buffer, size_t nbytes) {
	struct jpeg_error_mgr jerr;
	struct jpeg_decompress_struct cinfo;

	cinfo.err = jpeg_std_error(&jerr);
	jpeg_source_mgr src_mem;
	jpeg_create_decompress(&cinfo);
	JpegMemSrcMng::jpeg_mem_src(&cinfo, &src_mem, (void *)buffer, (long)nbytes);
	jpeg_read_header(&cinfo, TRUE);
	jpeg_start_decompress(&cinfo);

	return decodeJPEGIntoSurface(&cinfo);
}

void JpegUtil::JpegMemSrcMng::mem_init_source(j_decompress_ptr cinfo) {
}

void JpegUtil::JpegMemSrcMng::mem_term_source(j_decompress_ptr cinfo) {
}

boolean JpegUtil::JpegMemSrcMng::mem_fill_input_buffer(j_decompress_ptr cinfo) {
	ERREXIT( cinfo, JERR_INPUT_EOF );
	return TRUE;
}

void JpegUtil::JpegMemSrcMng::mem_skip_input_data(j_decompress_ptr cinfo, long num_bytes) {
    jpeg_source_mgr* src = (jpeg_source_mgr*)cinfo->src;

    if (1 > num_bytes) { return; }

	if (num_bytes < src->bytes_in_buffer) {
		src->next_input_byte += (size_t)num_bytes;
		src->bytes_in_buffer -= (size_t)num_bytes;
	} else {
		ERREXIT(cinfo, JERR_INPUT_EOF);
	}
}

void JpegUtil::JpegMemSrcMng::jpeg_mem_src(j_decompress_ptr cinfo, jpeg_source_mgr * const src, void const * const buffer, long nbytes) {
    src->init_source = mem_init_source;
    src->fill_input_buffer = mem_fill_input_buffer;
    src->skip_input_data = mem_skip_input_data;
    src->resync_to_restart = jpeg_resync_to_restart;
    src->term_source = mem_term_source;
    src->bytes_in_buffer = nbytes;
    src->next_input_byte = (JOCTET*)buffer;
    cinfo->src = src;
}
