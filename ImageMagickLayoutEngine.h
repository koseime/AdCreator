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

#include "AdLayoutEntry.h"

using namespace std;
using namespace MagickCore;

class ImageMagickLayoutEngine {
private:
	LayoutEngineManager layoutEngineManager;

	void drawText(MagickWand *backgroundMagickWand, DrawingWand *drawingWand, const AdLayoutEntry::TextEntry &textEntry, char *text);
	void scaleAndPlaceImage(MagickWand *backgroundMagickWand, MagickWand *magickWand, const AdLayoutEntry::ImageEntry &imageEntry);
public:
	ImageMagickLayoutEngine();
	virtual ~ImageMagickLayoutEngine();

	int importResources(const string &path);

	int create(const char* product_image_file, const char* title, const char* copy, const char* output);
	int create(Blob *productImage, const AdLayoutEntry &adLayoutEntry,
			const char *title, const char *copy, Blob *outputBlob);
	void createFromLayouts(const com::kosei::proto::AdComponents* product_info, vector<pair<string, Blob> > &generatedAds);
};

#endif /* IMAGEMAGICKLAYOUTENGINE_H_ */
