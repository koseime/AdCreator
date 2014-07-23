/*
 * JpegUtil.h
 *
 *  Created on: Jul 10, 2014
 *      Author: lanceriedel
 */

#ifndef JPEGUTIL_H_
#define JPEGUTIL_H_

#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <cairo/cairo.h>
#include <jpeglib.h>
#include <jerror.h>
#include <string.h>

class JpegUtil {
public:
	JpegUtil();
	virtual ~JpegUtil();

	cairo_surface_t * loadJpgFromFile(const char* Name);
	cairo_surface_t * loadJpgFromMemory(const char* buffer, size_t nbytes);
private:
	cairo_surface_t * decodeJPEGIntoSurface(jpeg_decompress_struct *info);

	class JpegMemSrcMng {
	public:
		static void jpeg_mem_src(j_decompress_ptr cinfo, jpeg_source_mgr * const src, void const * const buffer, long nbytes);
	private:
		static void mem_init_source(j_decompress_ptr cinfo);
		static void mem_term_source(j_decompress_ptr cinfo);
		static boolean mem_fill_input_buffer(j_decompress_ptr cinfo);
		static void mem_skip_input_data(j_decompress_ptr cinfo, long num_bytes);
	};
};

#endif /* JPEGUTIL_H_ */
