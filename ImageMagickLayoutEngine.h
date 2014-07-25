/*
 * LayoutEngine.h
 *
 *  Created on: Jul 10, 2014
 *      Author: lanceriedel
 */

#ifndef IMAGEMAGICKLAYOUTENGINE_H_
#define IMAGEMAGICKLAYOUTENGINE_H_

#include "AdComponentsMessages.pb.h"


class ImageMagickLayoutEngine {
public:
	ImageMagickLayoutEngine();
	virtual ~ImageMagickLayoutEngine();
	int create(const com::kosei::proto::AdComponents* product_info, const char* ad_text, const char* output);
	int create(const char* product_image_file, const char* ad_text, const char* output);


};

#endif /* IMAGEMAGICKLAYOUTENGINE_H_ */
