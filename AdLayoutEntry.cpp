/*
 * AdLayoutEntry.cpp
 *
 *  Created on: Aug 5, 2014
 *      Author: chantat
 */

#include "AdLayoutEntry.h"

using namespace std;

AdLayoutEntry::AdLayoutEntry(const string &_name, const string &template_id, const string &backgroundFilename, const string &logoFilename) {
	name = _name;
	if (template_id.compare("template_1") == 0) {
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry(logoFilename, 50, 50, 320 - 50, 0);
		product = ImageEntry("valid", 42, 42, 4, 4);

		description = TextEntry(string("@/Users/chantat/kosei/AdCreator/6457.ttf"), 12, 400, 0 ,0 , 60, 42);
		title = TextEntry(string("@/Users/chantat/kosei/AdCreator/6457.ttf"), 20, 700, 0 ,0 , 60, 23);
	} else if (template_id.compare("template_2") == 0) {
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry(logoFilename, 50, 50, 0, 0);
		product = ImageEntry("valid", 42, 42, 320 - 42 - 4, 4);

		description = TextEntry(string("@/Users/chantat/kosei/AdCreator/6457.ttf"), 12, 400, 0 ,0 , 60, 42);
		title = TextEntry(string("@/Users/chantat/kosei/AdCreator/6457.ttf"), 20, 700, 0 ,0 , 60, 23);
	} else if (template_id.compare("template_3") == 0) {
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry(logoFilename, 50, 50, 320 - 50, 0);
		product = ImageEntry("invalid", -1, -1, -1, -1);

		description = TextEntry(string("@/Users/chantat/kosei/AdCreator/6457.ttf"), 12, 400, 0 ,0 , 10, 42);
		title = TextEntry(string("@/Users/chantat/kosei/AdCreator/6457.ttf"), 20, 700, 0 ,0 , 10, 23);
	} else if (template_id.compare("template_4") == 0) {
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry("null", -1, -1, -1, -1);
		product = ImageEntry("valid", 42, 42, 320 - 42 - 4, 4);

		description = TextEntry(string("@/Users/chantat/kosei/AdCreator/6457.ttf"), 12, 400, 0 ,0 , 10, 42);
		title = TextEntry(string("@/Users/chantat/kosei/AdCreator/6457.ttf"), 20, 700, 0 ,0 , 10, 23);
	} else if (template_id.compare("template_5") == 0) {
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry(logoFilename, 50, 50, 320 - 50, 0);
		product = ImageEntry("valid", 42, 42, 320 - 50 - 42 - 4, 4);

		description = TextEntry(string("@/Users/chantat/kosei/AdCreator/6457.ttf"), 12, 400, 0 ,0 , 10, 42);
		title = TextEntry(string("@/Users/chantat/kosei/AdCreator/6457.ttf"), 20, 700, 0 ,0 , 10, 23);
	} else if (template_id.compare("template_6") == 0) {
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry(logoFilename, 50, 50, 0, 0);
		product = ImageEntry("invalid", -1, -1, -1, -1);

		description = TextEntry(string("@/Users/chantat/kosei/AdCreator/6457.ttf"), 12, 400, 0 ,0 , 60, 42);
		title = TextEntry(string("@/Users/chantat/kosei/AdCreator/6457.ttf"), 20, 700, 0 ,0 , 60, 23);
	} else if (template_id.compare("template_7") == 0) {
		background = ImageEntry(backgroundFilename, -1, -1, -1, -1);
		logo = ImageEntry("null", -1, -1, -1, -1);
		product = ImageEntry("valid", 42, 42, 4, 4);

		description = TextEntry(string("@/Users/chantat/kosei/AdCreator/6457.ttf"), 12, 400, 0 ,0 , 60, 42);
		title = TextEntry(string("@/Users/chantat/kosei/AdCreator/6457.ttf"), 20, 700, 0 ,0 , 60, 23);
	}
}




