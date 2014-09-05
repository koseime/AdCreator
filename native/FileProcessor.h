/*
 * FileProcessor.h
 *
 *  Created on: Jul 10, 2014
 *      Author: lanceriedel
 */

#ifndef FILEPROCESSOR_H_
#define FILEPROCESSOR_H_

#include "ProcessorCommand.h"
#include <string>

class FileProcessor {
public:
	FileProcessor();
	virtual ~FileProcessor();

	bool processFile(std::string const& fileName, ProcessorCommand* command);

};

#endif /* FILEPROCESSOR_H_ */
