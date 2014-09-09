/*
 * AdLayoutEntry.cpp
 *
 *  Created on: Aug 5, 2014
 *      Author: chantat
 */

#include "AdLayoutEntry.h"

#include <vector>
#include <string>
#include <cassert>

#include "jsonxx.h"

using namespace std;
using namespace jsonxx;

AdLayoutEntry::ImageEntry::ImageEntry(const string &_fileName, int _size_x, int _size_y, int _pos_x, int _pos_y) {
	fileName = _fileName;
	size_x = _size_x;
	size_y = _size_y;
	pos_x = _pos_x;
	pos_y = _pos_y;
}

//TODO: ADD CENTERED, LEFT,RIGHT
AdLayoutEntry::TextEntry::TextEntry(const Object &jsonObject, int _size_x, int _size_y, int _pos_x, int _pos_y) {
	defaultText = jsonObject.has<String>("text")?jsonObject.get<String>("text"):"No default text";
	fontName = jsonObject.has<String>("name")?jsonObject.get<String>("name"):"Arial";
	fontSize = jsonObject.has<Number>("size")?jsonObject.get<Number>("size"):20;
	fontWeight = jsonObject.has<Number>("weight")?jsonObject.get<Number>("weight"):400;
	fontColor = jsonObject.has<String>("color")?jsonObject.get<String>("color"):"black";
	fontType = jsonObject.has<String>("type")?jsonObject.get<String>("type"):"normal";

	size_x = _size_x;
	size_y = _size_y;
	pos_x = _pos_x;
	pos_y = _pos_y;
}

AdLayoutEntry::AdLayoutEntry(const string &jsonString) {
	Object object;
	object.parse(jsonString);

	assert(object.has<String>("name"));
	name = object.get<String>("name");
	backgroundColor = object.has<String>("background_color")?object.get<String>("background_color"):"white";
	string backgroundFilename = object.has<String>("background_filename")?object.get<String>("background_filename"):"null";
	string logoFilename = object.has<String>("logo_filename")?object.get<String>("logo_filename"):"null";
	string templateId = object.has<String>("template_id")?object.get<String>("template_id"):"template_1";
	assert(object.has<Object>("title_font"));
	const Object &titleFontJson = object.get<Object>("title_font");
	assert(object.has<Object>("description_font"));
	const Object &descriptionFontJson = object.get<Object>("description_font");

	bool isTemplateSet = false;
	if (templateId.compare("template_1") == 0) {
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry(logoFilename, 50, 50, 320 - 50, 0);
		product = ImageEntry("valid", 46, 46, 2, 2);

		description = TextEntry(descriptionFontJson, 0 ,0 , 55, 31);
		title = TextEntry(titleFontJson, 0 ,0 , 55, 10);
		isTemplateSet = true;
	} else if (templateId.compare("template_2") == 0) {
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry(logoFilename, 50, 50, 0, 0);
		product = ImageEntry("valid", 46, 46, 320 - 46 - 2, 2);

		description = TextEntry(descriptionFontJson, 0 ,0 , 60, 31);
		title = TextEntry(titleFontJson, 0 ,0 , 60, 10);
		isTemplateSet = true;
	} else if (templateId.compare("template_3") == 0) {
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry(logoFilename, 50, 50, 320 - 50, 0);
		product = ImageEntry("invalid", -1, -1, -1, -1);

		description = TextEntry(descriptionFontJson, 0 ,0 , 10, 31);
		title = TextEntry(titleFontJson, 0 ,0 , 10, 10);
		isTemplateSet = true;
	} else if (templateId.compare("template_4") == 0) {
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry("invalid", -1, -1, -1, -1);
		product = ImageEntry("valid", 46, 46, 320 - 46 - 2, 2);

		description = TextEntry(descriptionFontJson, 0 ,0 , 10, 31);
		title = TextEntry(titleFontJson, 0 ,0 , 10, 10);
		isTemplateSet = true;
	} else if (templateId.compare("template_5") == 0) {
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry(logoFilename, 50, 50, 320 - 50, 0);
		product = ImageEntry("valid", 46, 46, 320 - 50 - 46 - 2, 2);

		description = TextEntry(descriptionFontJson, 0 ,0 , 10, 31);
		title = TextEntry(titleFontJson, 0 ,0 , 10, 10);
		isTemplateSet = true;
	} else if (templateId.compare("template_6") == 0) {
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry(logoFilename, 50, 50, 0, 0);
		product = ImageEntry("invalid", -1, -1, -1, -1);

		description = TextEntry(descriptionFontJson, 0 ,0 , 60, 31);
		title = TextEntry(titleFontJson, 0 ,0 , 60, 10);
		isTemplateSet = true;
	} else if (templateId.compare("template_7") == 0) {
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry("invalid", -1, -1, -1, -1);
		product = ImageEntry("valid", 46, 46, 2, 2);

		description = TextEntry(descriptionFontJson, 0 ,0 , 60, 31);
		title = TextEntry(titleFontJson, 0 ,0 , 60, 10);
		isTemplateSet = true;
	}
	assert(isTemplateSet);
}




