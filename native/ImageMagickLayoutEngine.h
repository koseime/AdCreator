/*
 * LayoutEngine.h
 *
 *  Created on: Jul 10, 2014
 *      Author: lanceriedel
 */

#ifndef IMAGEMAGICKLAYOUTENGINE_H_
#define IMAGEMAGICKLAYOUTENGINE_H_

#include "LayoutEngineManager.h"
#include "Magick++.h"

#include <string>
#include <vector>

#include "AdLayoutEntry.h"

using namespace std;
using namespace MagickCore;

class ImageMagickLayoutEngine {
private:
	bool isResourceImported;
	LayoutEngineManager layoutEngineManager;
	PixelWand *pixelWand;
	DrawingWand *drawingWand;

	void drawText(MagickWand *backgroundMagickWand, const AdLayoutEntry::TextEntry &textEntry, const string &text);
	void cropSquare(MagickWand *magickWand);

	void scaleAndExtendImage(MagickWand *backgroundMagickWand, MagickWand *magickWand,
			const AdLayoutEntry::ImageEntry &imageEntry);
	void createRoundedRectangleMask(MagickWand *maskMagickWand, int size_x, int size_y);

public:
	ImageMagickLayoutEngine();
	virtual ~ImageMagickLayoutEngine();

	int importResources(const string &path);

	int create(const string &product_image_file, const string &title,
			const string &copy, const string &backgroundColor, const string &output_file);
	int create(const string &productImage, const string &backgroundBlob,
			const string &logoBlob, const AdLayoutEntry &adLayoutEntry, const string &title,
			const string &copy, const string &backgroundColor, string *outputBlob);
	int create(const string &productImage, const AdLayoutEntry &adLayoutEntry,
			const string &title, const string &copy, const string &backgroundColor, string *outputBlob);
	void createAllLayouts(const string &productImage, const string &title,
			const string &copy, vector<pair<string, string> > *generatedAds);
};

#endif /* IMAGEMAGICKLAYOUTENGINE_H_ */
