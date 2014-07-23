/*
 * LayoutEngine.h
 *
 *  Created on: Jul 10, 2014
 *      Author: lanceriedel
 */

#ifndef LAYOUTENGINE_H_
#define LAYOUTENGINE_H_

#include "JpegUtil.h"
#include "AdComponentsMessages.pb.h"


class LayoutEngine {
public:
	LayoutEngine();
	virtual ~LayoutEngine();
	int create(const com::kosei::proto::AdComponents* product_info, const char* ad_text, const char* output);
	int create(const char* product_image_file, const char* ad_text, const char* output);

private:
	JpegUtil jpegUtil;
};

#endif /* LAYOUTENGINE_H_ */
