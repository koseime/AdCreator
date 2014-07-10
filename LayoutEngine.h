/*
 * LayoutEngine.h
 *
 *  Created on: Jul 10, 2014
 *      Author: lanceriedel
 */

#ifndef LAYOUTENGINE_H_
#define LAYOUTENGINE_H_

#include "JpegUtil.h"



class LayoutEngine {
public:
	LayoutEngine();
	virtual ~LayoutEngine();
	int create(char* image_product_file, char* ad_text, char* output);


private:
	JpegUtil jpegUtil;
};

#endif /* LAYOUTENGINE_H_ */
