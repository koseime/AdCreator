/*
 * AdLayoutEntry.h
 *
 *  Created on: Aug 5, 2014
 *      Author: chantat
 */

#ifndef ADLAYOUTENTRY_H_
#define ADLAYOUTENTRY_H_

#include <string>

#include "jsonxx.h"

using namespace std;
using namespace jsonxx;

class AdLayoutEntry {
public:
	class ImageEntry {
	public:
		string fileName;
		int size_x;
		int size_y;
		int pos_x;
		int pos_y;
		ImageEntry() {};
		ImageEntry(const string &_fileName, int _size_x, int _size_y, int _pos_x, int _pos_y);
	};

	class TextEntry {
	public:
		string defaultText;
		string fontName;
		int fontSize;
		int fontWeight;
		string fontColor;
		string fontType;
		int size_x;
		int size_y;
		int pos_x;
		int pos_y;
		bool skip;
		TextEntry() ;
		TextEntry(const Object &fontJsonObject, int _size_x, int _size_y, int _pos_x, int _pos_y);
	};



	string name;

	string backgroundColor;

	ImageEntry background;
	ImageEntry logo;
	ImageEntry product;

	TextEntry title;
	TextEntry description;
	AdLayoutEntry(const string &jsonString);
};



#endif /* ADLAYOUTENTRY_H_ */
