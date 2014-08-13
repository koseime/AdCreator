#include "LayoutEngineManager.h"

#include <iostream>
#include <sstream>
#include <fstream>
#include <stdlib.h>
#include <string>
#include <cstring>

#include "Magick++.h"
#include "archive.h"
#include "archive_entry.h"

#include "AdLayoutEntry.h"

using namespace std;
using namespace Magick;


LayoutEngineManager::LayoutEngineManager() {
}

LayoutEngineManager::~LayoutEngineManager() {

}

void LayoutEngineManager::importAdLayouts(char *fileContent, size_t fileLen) {
	// TODO: write new function
	istringstream adLayoutFile(string(fileContent, fileLen));
	string line;
	while (getline(adLayoutFile, line)) {
		char *line_cstr = new char[line.length() + 1];
		strcpy(line_cstr, line.c_str());

		vector<string> tokens;
		char *pch = strtok(line_cstr, "\t");
		while (pch != NULL) {
			tokens.push_back(pch);
			pch = strtok(NULL, "\t");
		}

		if (tokens.size() == 4) {
			adLayouts.push_back(AdLayoutEntry(tokens[0], tokens[1], tokens[2], tokens[3]));
		} else {
			cerr << "Trouble parsing line: " + line << endl;
		}
		delete[] line_cstr;
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
			idToBlob[string(entry_pathname)].update(fileContents, entry_size);
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

Blob* LayoutEngineManager::getImageBlob(const string &filename) {
	map<string, Blob>::iterator it = idToBlob.find(filename);
	if (it == idToBlob.end()) { return NULL; }
	return &(it->second);
}
