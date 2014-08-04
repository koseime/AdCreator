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
	private:
		string id;
		string template_id;
		string logo_filename;
		string background_filename;

	public:
		AdLayoutEntry(const string &_id, const string &_template_id, const string &_logo_filename, const string &_background_filename) {
			id = _id;
			template_id = _template_id;
			logo_filename = _logo_filename;
			background_filename = _background_filename;
		}
		virtual ~AdLayoutEntry() { }

		string getId() {
			return id;
		}

		string getTemplateId() {
			return template_id;
		}

		string getLogoFilename() {
			return logo_filename;
		}

		string getBackgroundFilename() {
			return  background_filename;
		}
	};
private:
	map <string, Blob> idToBlob;
	vector<AdLayoutEntry> adLayouts;

	void importAdLayouts(char *fileContent, size_t fileLen);
public:

	LayoutEngineManager();
	virtual ~LayoutEngineManager();

	int importImagesAndLayouts(const string &path);
	Blob *getImageBlob(const string &filename);
	void getAdLayouts(vector<AdLayoutEntry> &_adLayouts);
};

#endif /* LAYOUTENGINEMANAGER_H_ */
