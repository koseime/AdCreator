/*
 * LayoutEngine.h
 *
 *  Created on: Jul 10, 2014
 *      Author: lanceriedel
 */

#ifndef IMAGEMAGICKLAYOUTENGINE_H_
#define IMAGEMAGICKLAYOUTENGINE_H_

#include "AdComponentsMessages.pb.h"

#include "LayoutEngineManager.h"
#include "Magick++.h"

#include <string>
#include <vector>

using namespace std;
using namespace Magick;

class ImageMagickLayoutEngine {
private:
	LayoutEngineManager layoutEngineManager;

public:
	ImageMagickLayoutEngine();
	virtual ~ImageMagickLayoutEngine();

	int importResources(const string &path);

	int create(const com::kosei::proto::AdComponents* product_info, const char* ad_text, const char* output);
	int create(const char* product_image_file, const char* ad_text, const char* output);
	char *createToBuffer(const com::kosei::proto::AdComponents* product_info, const char* ad_text, size_t *length);
	void createFromLayouts(const com::kosei::proto::AdComponents* product_info, vector<pair<string, Blob> > &generatedAds);
};

#endif /* IMAGEMAGICKLAYOUTENGINE_H_ */
