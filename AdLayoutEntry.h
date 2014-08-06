/*
 * AdLayoutEntry.h
 *
 *  Created on: Aug 5, 2014
 *      Author: chantat
 */

#ifndef ADLAYOUTENTRY_H_
#define ADLAYOUTENTRY_H_

#include <string>

using namespace std;

class AdLayoutEntry {
public:
	struct ImageEntry {
		string fileName;
		int size_x;
		int size_y;
		int pos_x;
		int pos_y;
		ImageEntry() {}
		ImageEntry(const string &_fileName, int _size_x, int _size_y, int _pos_x, int _pos_y) {
			fileName = _fileName;
			size_x = _size_x;
			size_y = _size_y;
			pos_x = _pos_x;
			pos_y = _pos_y;
		};
	};

	struct TextEntry {
		string fontName;
		int fontSize;
		int fontWeight;
		int size_x;
		int size_y;
		int pos_x;
		int pos_y;
		TextEntry() {}
		TextEntry(const string &_fontName, int _fontSize, int _fontWeight, int _size_x, int _size_y, int _pos_x, int _pos_y) {
			fontName = _fontName;
			fontSize = _fontSize;
			fontWeight = _fontWeight;
			size_x = _size_x;
			size_y = _size_y;
			pos_x = _pos_x;
			pos_y = _pos_y;
		}
	};

	string name;

	ImageEntry background;
	ImageEntry logo;
	ImageEntry product;

	TextEntry title;
	TextEntry description;
	AdLayoutEntry(const string &_name, const string &template_id, const string &backgroundFilename, const string &logoFilename);
};



#endif /* ADLAYOUTENTRY_H_ */
