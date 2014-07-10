/*
 * FileProcessor.cpp
 *
 *  Created on: Jul 10, 2014
 *      Author: lanceriedel
 */

#include "FileProcessor.h"
#include <iostream>
#include <fstream>

FileProcessor::FileProcessor() {
	// TODO Auto-generated constructor stub

}

FileProcessor::~FileProcessor() {
	// TODO Auto-generated destructor stub
}


bool FileProcessor::processFile(std::string const& filePath, ProcessorCommand* command) {

	std::string line;
	//_indexFileReader = new std::ifstream(index.data(), std::ios_base::in | std::ios_base::binary);
	std::ios_base::sync_with_stdio(false);

	std::ifstream* infile = new std::ifstream((const char*)filePath.data(), std::ios_base::in);
	if (infile->is_open()) {
		int count = 0;
		while (!infile->eof() ) {
			std::getline (*infile, line);
			//cout << line << endl;

			command->setLine(&line, ++count);
			command->invoke();
			if (count%100000==0) {
#ifdef DEBUG_ENABLED
				printf ("Processed: %d lines\n", count);
				std::cout.flush();
#endif
			}
		}
#ifdef DEBUG_ENABLED
		printf ("Processing Complete: %d lines\n", count);
#endif
		infile->close();
		delete infile;
                return true;
	} else {
		printf("File Processor Error opening file: %s", filePath.data());
		std::cout.flush();
		delete infile;
                return false;
	}
}


