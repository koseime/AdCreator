#include "LayoutEngineManager.h"

#include <iostream>
#include <sstream>
#include <fstream>
#include <stdlib.h>
#include <string>
#include <cstring>

#include "archive.h"
#include "archive_entry.h"

#include "AdLayoutEntry.h"

using namespace std;

const string LayoutEngineManager::NOT_EXIST = "";

LayoutEngineManager::LayoutEngineManager() {
}

LayoutEngineManager::~LayoutEngineManager() {

}

void LayoutEngineManager::importAdLayouts(char *fileContent, size_t fileLen) {
	istringstream adLayoutFile(string(fileContent, fileLen));
	string line;
	while (getline(adLayoutFile, line)) {
		if (!line.empty()) {
			adLayouts.push_back(AdLayoutEntry(line));
		}
	}
}

int LayoutEngineManager::getAdLayoutsSize() {
	return adLayouts.size();
}

AdLayoutEntry LayoutEngineManager::getAdLayouts(int index) {
	return adLayouts[index];
}

int LayoutEngineManager::importImagesAndLayouts(const string &path) {
	struct archive *a;
	struct archive_entry *entry;
	int r;

	a = archive_read_new();
	archive_read_support_filter_all(a);
	archive_read_support_format_all(a);

	FILE *fin = fopen(path.c_str(), "r");
	if (fin == NULL) {
		cerr << path + " not found";
		return -1;
	}

	r = archive_read_open_FILE(a, fin);
	if (r != ARCHIVE_OK) {
		cerr << "Error reading " + path;
		return -1;
	}

	bool manFileFound = false;
	while (archive_read_next_header(a, &entry) == ARCHIVE_OK) {
		const char* entry_pathname = archive_entry_pathname(entry);
		size_t entry_size = archive_entry_size(entry);

		char *fileContents = new char[entry_size];
		int retVal = archive_read_data(a, fileContents, entry_size);

		if (retVal <= 0) {
			cerr << "Error reading " + string(entry_pathname) + " from " + path;
		} else if (strcmp(entry_pathname, "manifest.properties") == 0) {
			manFileFound = true;
			importAdLayouts(fileContents, entry_size);
		} else {
			idToBlob[string(entry_pathname)] = string(fileContents, entry_size);
		}

		delete[] fileContents;
	}

	if (!manFileFound) {
		cerr << "manifest.properties not found";
		return -1;
	}

	r = archive_read_free(a);
	if (r != ARCHIVE_OK) {
		cerr << "Error reading " + path;
		return -1;
	}

	return 0;
}

const string& LayoutEngineManager::getImageBlob(const string &filename) {
	map<string, string>::iterator it = idToBlob.find(filename);
	if (it == idToBlob.end()) { return NOT_EXIST; }
	return it->second;
}

void LayoutEngineManager::addImageBlob(const string &name, const string &blob) {
	idToBlob[name] = blob;
}
