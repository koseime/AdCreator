/*
 * BulkProcessor.h
 *
 *  Created on: Jul 10, 2014
 *      Author: lanceriedel
 */

#ifndef BULKPROCESSOR_H_
#define BULKPROCESSOR_H_

#include <string>
#include <stdint.h>

#include "Tokenizer.h"
#include "ProcessorCommand.h"
#include "FileProcessor.h"
#include "ImageMagickLayoutEngine.h"

class BulkProcessor: public ProcessorCommand  {
public:
	BulkProcessor();
	virtual ~BulkProcessor();



	void invoke();
	void start(std::string const& input, std::string const& resultOutputDir, std::string const& inputImageDir);
	void setLine(std::string* line, int lineCount);

private:
	void parseline(std::string& line, std::string& id, std::string& productPic, std::string& productDescription);
	void process(std::string const& fileToProcess);

	std::string _outputDir;
	std::string _inputImageDir;
	std::string* _line;
	int _lineCount;
	ImageMagickLayoutEngine layoutEngine;
};

#endif /* BULKPROCESSOR_H_ */
