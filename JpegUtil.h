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
#include <string.h>

class JpegUtil {
public:
	JpegUtil();
	virtual ~JpegUtil();

	cairo_surface_t * loadJpg(const char* Name);
private:
	cairo_surface_t * decodeJPEGIntoSurface(jpeg_decompress_struct *info);
};

#endif /* JPEGUTIL_H_ */
