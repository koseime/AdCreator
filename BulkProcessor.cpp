/*
 * BulkProcessor.cpp
 *
 *  Created on: Jul 10, 2014
 *      Author: lanceriedel
 */

#include "BulkProcessor.h"
#include "Tokenizer.h"

BulkProcessor::BulkProcessor() {

}

BulkProcessor::~BulkProcessor() {

}

void BulkProcessor::start(std::string const& input,
		std::string const& resultOutputDir,  std::string const& inputImageDir) {
	std::cout << "STARTING TO PROCESS..." << std::endl;
	_outputDir = resultOutputDir;
	_inputImageDir =  inputImageDir;

	process(input);

	std::cout << "*****DONE******..." << std::endl;


}

void BulkProcessor::parseline(std::string& line, std::string& id, std::string& productPic, std::string& productDescription) {
	if (line.size() < 2) {
		return;
	}

	std::vector<std::string> parsedFields;
	Tokenizer::tokenize(line, parsedFields, "\t");
	std::cout << "\t>>>" << line << std::endl;

	if (!parsedFields.empty()) {
		id = parsedFields[0].data();
		productPic = _inputImageDir + "/" + parsedFields[1].data();
		productDescription = parsedFields[2].data();
	}
}



void BulkProcessor::invoke() {
	std::string productPic;
	std::string productDescription;
	std::string id;

	if (_line == NULL || _line->size() < 2)
		return;

	parseline(*_line, id, productPic, productDescription);
	std::string output = _outputDir + "/" + id + ".png";

	layoutEngine.create((const char*) productPic.c_str(),  productDescription.c_str(),  output.c_str());

}

void BulkProcessor::process(std::string const& inputFile) {
	FileProcessor* fp = new FileProcessor();
	std::cout << " BulkProcessor::process: " << inputFile
			<< std::endl;
	fp->processFile(inputFile, this);
	delete fp;
}

void BulkProcessor::setLine(std::string* line, int lineCount) {
	_line = line;
	_lineCount = lineCount;

}



