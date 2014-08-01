#include "LayoutEngineManager.h"

#include <iostream>
#include <sstream>
#include <fstream>
#include <stdlib.h>
#include <string>

#include "Magick++.h"
#include "archive.h"
#include "archive_entry.h"


using namespace std;
using namespace Magick;


LayoutEngineManager::LayoutEngineManager() {
}

LayoutEngineManager::~LayoutEngineManager() {

}

void LayoutEngineManager::importAdLayouts(char *fileContent, size_t fileLen) {
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

int LayoutEngineManager::importImagesAndLayouts(const string &path) {
	struct archive *a;
	struct archive_entry *entry;
	int r;

	a = archive_read_new();
	archive_read_support_filter_all(a);
	archive_read_support_format_all(a);

	FILE *fin = fopen(path.c_str(), "r");
	if (fin == NULL) { return -1; }

	r = archive_read_open_FILE(a, fin);
	if (r != ARCHIVE_OK) { return -1; }

	while (archive_read_next_header(a, &entry) == ARCHIVE_OK) {
		const char* entry_pathname = archive_entry_pathname(entry);

		size_t entry_size = archive_entry_size(entry);

		char *fileContents = new char[entry_size];
		archive_read_data(a, fileContents, entry_size);

		if (strcmp(entry_pathname, "manifest.properties") == 0) {
			importAdLayouts(fileContents, entry_size);
		} else {
			idToBlob[string(entry_pathname)].update(fileContents, entry_size);
		}
		delete[] fileContents;
	}

	r = archive_read_free(a);
	if (r != ARCHIVE_OK) { return -1; }

	return 0;
}

Blob* LayoutEngineManager::getImageBlob(const string &filename) {
	map<string, Blob>::iterator it = idToBlob.find(filename);
	if (it == idToBlob.end()) { return NULL; }
	return &(it->second);
}
