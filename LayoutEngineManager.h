#ifndef LAYOUTENGINEMANAGER_H_
#define LAYOUTENGINEMANAGER_H_

#include <map>
#include <string>

#include "Magick++.h"

using namespace std;
using namespace Magick;

class LayoutEngineManager {
public:
	class AdLayoutEntry {
	public:
		string id;
		string template_id;
		string logo_filename;
		string background_filename;
		AdLayoutEntry(const string &_id, const string &_template_id, const string &_logo_filename, const string &_background_filename) {
			id = _id;
			template_id = _template_id;
			logo_filename = _logo_filename;
			background_filename = _background_filename;
		}
		virtual ~AdLayoutEntry() { }
	};
private:
	map <string, Blob> idToBlob;

	void importAdLayouts(char *fileContent, size_t fileLen);
public:
	vector<AdLayoutEntry> adLayouts;

	LayoutEngineManager();
	virtual ~LayoutEngineManager();

	int importImagesAndLayouts(const string &path);
	Blob *getImageBlob(const string &filename);
};

#endif /* LAYOUTENGINEMANAGER_H_ */
