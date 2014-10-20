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
#include <iostream>


#include "jsonxx.h"
#include "Tokenizer.h"


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
	defaultText = jsonObject.has<String>("text")?jsonObject.get<String>("text"):"";
	fontName = jsonObject.has<String>("name")?jsonObject.get<String>("name"):"Arial";
	fontSize = jsonObject.has<Number>("size")?jsonObject.get<Number>("size"):20;
	fontWeight = jsonObject.has<Number>("weight")?jsonObject.get<Number>("weight"):400;
	fontColor = jsonObject.has<String>("color")?jsonObject.get<String>("color"):"black";
	fontType = jsonObject.has<String>("type")?jsonObject.get<String>("type"):"normal";

	size_x = _size_x;
	size_y = _size_y;
	pos_x = _pos_x;
	pos_y = _pos_y;
	skip = false;
}

AdLayoutEntry::TemplateEntry::TemplateEntry(const String &templateEntryAsString) {

	std::vector<std::string> parsedFields;
    Tokenizer::tokenize(templateEntryAsString, parsedFields, ":");

    assert(!parsedFields.empty());

    size_x = atoi(parsedFields[0].data());
	size_y = atoi(parsedFields[1].data());
	pos_x = atoi(parsedFields[2].data());
	pos_y = atoi(parsedFields[3].data());
}

AdLayoutEntry::TextEntry::TextEntry() {
    skip = true;
}

AdLayoutEntry::AdLayoutEntry(const string &jsonString) {
	Object object;
	object.parse(jsonString);

	assert(object.has<String>("name"));
	name = object.get<String>("name");
	backgroundColor = object.has<String>("background_color")?object.get<String>("background_color"):"white";
	string backgroundFilename = object.has<String>("background_filename")?object.get<String>("background_filename"):"null";
	string logoFilename = object.has<String>("logo_filename")?object.get<String>("logo_filename"):"null";
    string callToActionFilename = object.has<String>("calltoaction_filename")?object.get<String>("calltoaction_filename"):"null";
	string templateId = object.has<String>("template_id")?object.get<String>("template_id"):"template_1";
	assert(object.has<Object>("title_font"));
	const Object &titleFontJson = object.get<Object>("title_font");
	assert(object.has<Object>("description_font"));
	const Object &descriptionFontJson = object.get<Object>("description_font");

	assert(object.has<Object>("price_font"));
    const Object &priceFontJson = object.get<Object>("price_font");

    //NORMAL 210,20
     bool isTemplateSet = false;
	if (templateId.compare("__embedded")== 0) {
	   	assert(object.has<Object>("embedded_template"));
        const Object &embedded_template = object.get<Object>("embedded_template");

        AdLayoutEntry::TemplateEntry logoTemplateEntry = AdLayoutEntry::TemplateEntry( embedded_template.has<String>("logo")?embedded_template.get<String>("logo"):"null");
        logo = ImageEntry(logoFilename, logoTemplateEntry.size_x,logoTemplateEntry.size_y,logoTemplateEntry.pos_x,logoTemplateEntry.pos_y);

        AdLayoutEntry::TemplateEntry productTemplateEntry = AdLayoutEntry::TemplateEntry( embedded_template.has<String>("product")?embedded_template.get<String>("product"):"null");
        product = ImageEntry("valid", productTemplateEntry.size_x, productTemplateEntry.size_y, productTemplateEntry.pos_x, productTemplateEntry.pos_y);

        AdLayoutEntry::TemplateEntry priceTemplateEntry = AdLayoutEntry::TemplateEntry( embedded_template.has<String>("price")?embedded_template.get<String>("price"):"null");
        price = TextEntry(priceFontJson, priceTemplateEntry.size_x, priceTemplateEntry.size_y, priceTemplateEntry.pos_x, priceTemplateEntry.pos_y);

         AdLayoutEntry::TemplateEntry descTemplateEntry = AdLayoutEntry::TemplateEntry( embedded_template.has<String>("description")?embedded_template.get<String>("description"):"null");
                description = TextEntry(descriptionFontJson, descTemplateEntry.size_x, descTemplateEntry.size_y, descTemplateEntry.pos_x, descTemplateEntry.pos_y);

        AdLayoutEntry::TemplateEntry titleTemplateEntry = AdLayoutEntry::TemplateEntry( embedded_template.has<String>("title")?embedded_template.get<String>("title"):"null");
        title = TextEntry(titleFontJson, titleTemplateEntry.size_x, titleTemplateEntry.size_y, titleTemplateEntry.pos_x, titleTemplateEntry.pos_y);

        if ( embedded_template.has<String>("calltoaction")) {
            AdLayoutEntry::TemplateEntry callToActionTemplateEntry = AdLayoutEntry::TemplateEntry( embedded_template.has<String>("calltoaction")?embedded_template.get<String>("calltoaction"):"null");
            calltoaction = ImageEntry(callToActionFilename, callToActionTemplateEntry.size_x,callToActionTemplateEntry.size_y,callToActionTemplateEntry.pos_x,callToActionTemplateEntry.pos_y);
        }

        AdLayoutEntry::TemplateEntry mainTemplateEntry = AdLayoutEntry::TemplateEntry( embedded_template.has<String>("main")?embedded_template.get<String>("main"):"null");

        size_x=mainTemplateEntry.size_x;
        size_y=mainTemplateEntry.size_y;


        background = ImageEntry(backgroundFilename, -1, -1, -1, -1);

        isTemplateSet = true;


	}


	//PREDEFINED TEMPLATES
	else if (templateId.compare("template_1") == 0) {
	    size_x = 320;
	    size_y = 50;
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry(logoFilename, 50, 50, 320 - 50, 0);
		product = ImageEntry("valid", 45, 45, 2, 2);

		description = TextEntry(descriptionFontJson, 210, 20, 55, 28);
		title = TextEntry(titleFontJson, 210, 23, 55, 8);
		isTemplateSet = true;
	} else if (templateId.compare("template_2") == 0) {
	    size_x = 320;
	    size_y = 50;
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry(logoFilename, 50, 50, 0, 0);
		product = ImageEntry("valid", 45, 45, 320 - 45 - 2, 2);

		description = TextEntry(descriptionFontJson, 210, 20, 55, 28);
		title = TextEntry(titleFontJson, 210, 23, 55, 8);
		isTemplateSet = true;
	} else if (templateId.compare("template_3") == 0) {
	    size_x = 320;
	    size_y = 50;
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry(logoFilename, 50, 50, 320 - 50, 0);
		product = ImageEntry("invalid", -1, -1, -1, -1);

		description = TextEntry(descriptionFontJson, 250, 20, 10, 28);
		title = TextEntry(titleFontJson, 250, 25, 10, 8);
		isTemplateSet = true;
	} else if (templateId.compare("template_4") == 0) {
	    size_x = 320;
	    size_y = 50;
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry("invalid", -1, -1, -1, -1);
		product = ImageEntry("valid", 45, 45, 320 - 45 - 2, 2);

		description = TextEntry(descriptionFontJson, 250 ,20 , 10, 28);
		title = TextEntry(titleFontJson, 250, 25 , 10, 8);
		isTemplateSet = true;
	} else if (templateId.compare("template_5") == 0) {
	    size_x = 320;
	    size_y = 50;
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry(logoFilename, 50, 50, 320 - 50, 0);
		product = ImageEntry("valid", 45, 45, 320 - 50 - 45 - 2, 2);

		description = TextEntry(descriptionFontJson, 210, 20, 10, 28);
		title = TextEntry(titleFontJson, 210, 23, 10, 8);
		isTemplateSet = true;
	} else if (templateId.compare("template_6") == 0) {
	    size_x = 320;
	    size_y = 50;
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry(logoFilename, 50, 50, 0, 0);
		product = ImageEntry("invalid", -1, -1, -1, -1);

		description = TextEntry(descriptionFontJson, 250, 20, 55, 28);
		title = TextEntry(titleFontJson, 250, 25, 55, 8);
		isTemplateSet = true;
	} else if (templateId.compare("template_7") == 0) {
	    size_x = 320;
	    size_y = 50;
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry("invalid", -1, -1, -1, -1);
		product = ImageEntry("valid", 45, 45, 2, 2);

		description = TextEntry(descriptionFontJson, 250, 20, 55, 28);
		title = TextEntry(titleFontJson, 250, 25, 55, 8);
		isTemplateSet = true;
	}  else if (templateId.compare("template_8") == 0) {
	    size_x = 320;
	    size_y = 50;
        background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
        logo = ImageEntry(logoFilename, 50, 50, 320 - 50, 0);
        product = ImageEntry("valid", 45, 45, 2, 2);

        title = TextEntry(titleFontJson, 210, 40, 55, 7);
        description = TextEntry();
        isTemplateSet = true;
    }
	assert(isTemplateSet);
}




