/*
 * ProcessorCommand.h
 *
 *  Created on: Jul 10, 2014
 *      Author: lanceriedel
 */

#ifndef PROCESSORCOMMAND_H_
#define PROCESSORCOMMAND_H_

#include <string>

class ProcessorCommand {
public:
	virtual void setLine(std::string* line, int lineCount) = 0;
	virtual void invoke() = 0;
};

#endif /* PROCESSORCOMMAND_H_ */
