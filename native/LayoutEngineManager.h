#ifndef LAYOUTENGINEMANAGER_H_
#define LAYOUTENGINEMANAGER_H_

#include <map>
#include <string>

#include "AdLayoutEntry.h"

using namespace std;

class LayoutEngineManager {
public:
	static const string NOT_EXIST;

private:
	map <string, string> idToBlob;
	vector<AdLayoutEntry> adLayouts;

	void importAdLayouts(const char *fileContent, size_t fileLen);
public:
	LayoutEngineManager();
	virtual ~LayoutEngineManager();

	int importImagesAndLayouts(const string &path);
	void addImageBlob(const string &name, const string &blob);
	const string &getImageBlob(const string &filename);
	int getAdLayoutsSize();
	AdLayoutEntry getAdLayouts(int index);
};

#endif /* LAYOUTENGINEMANAGER_H_ */
