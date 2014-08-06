#ifndef LAYOUTENGINEMANAGER_H_
#define LAYOUTENGINEMANAGER_H_

#include <map>
#include <string>

#include "Magick++.h"

#include "AdLayoutEntry.h"

using namespace std;
using namespace Magick;

class LayoutEngineManager {
public:

private:
	map <string, Blob> idToBlob;
	vector<AdLayoutEntry> adLayouts;

	void importAdLayouts(char *fileContent, size_t fileLen);
public:

	LayoutEngineManager();
	virtual ~LayoutEngineManager();

	int importImagesAndLayouts(const string &path);
	Blob *getImageBlob(const string &filename);
	int getAdLayoutsSize();
	AdLayoutEntry getAdLayouts(int index);
};

#endif /* LAYOUTENGINEMANAGER_H_ */
