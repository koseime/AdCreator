/*
 * AdCreatorCLI.cpp
 *
 *  Created on: Jul 10, 2014
 *      Author: lanceriedel
 */

#include "AdCreatorCLI.h"
#include <iostream>
#include <getopt.h>
#include "ImageMagickLayoutEngine.h"
#include "BulkProcessor.h"

AdCreatorCLI::AdCreatorCLI() {
	// TODO Auto-generated constructor stub

}

AdCreatorCLI::~AdCreatorCLI() {
	// TODO Auto-generated destructor stub
}




//./gl-cairo-simple vacuum.png "We think you would like this vacuum" out.png


static void usage(const char *prog) {
	std::cout << "Create an ad from parts" << std::endl << "usage: "
			<< std::endl << "\t" << prog << " {options}" << std::endl;
	std::cout << "options: " << std::endl
			<< "\t-o <output directory> : bulk - Output directory name" << std::endl
			<< "\t-i <input file>  : bulk - Input file to bulk process ads format:[id<tab><image path><description>" << std::endl
			<< "\t-g <input image dir>  : directory with input images" << std::endl
			<< "\t-x <eXecution type>  : bulk or single" << std::endl
			<< "\t-p <product image>  : single - product image" << std::endl
			<< "\t-d <ad text>  : single - ad text" << std::endl
			<< "\t-r <ad result>  : single - ad result output" << std::endl
			<< std::endl << std::endl;
}

int main(int argc, char *argv[])
{
	std::string input, inputimagedir, outputdir, exectype, prodimage, adtext, adresult;
	int opt;
	while ((opt = getopt(argc, argv, "x:i:o:p:d:r:g:h")) != EOF) {
		switch (opt) {
		case 'x':
			exectype = optarg;
			break;
		case 'i':
			input = optarg;
			break;
		case 'o':
			outputdir = optarg;
			break;
		case 'p':
			prodimage = optarg;
			break;
		case 'd':
			adtext = optarg;
			break;
		case 'r':
			adresult = optarg;
			break;
		case 'g':
			inputimagedir = optarg;
			break;
		case 'h':
		default:
			usage(argv[0]);
			return 1;
			break;
		}
	}


	if (exectype.compare("single") == 0) {
		std::cout << "Create an ad from parts - single use: " << "image: " << prodimage << " text:" << adtext << std::endl;
		//Load a few images from files

		ImageMagickLayoutEngine engine;
		return engine.create(prodimage.c_str(),  adtext.c_str(),  adresult.c_str());
	} else if (exectype.compare("bulk") == 0) {
		std::cout << "Create an ad from parts - single use: " << "image: " << prodimage << " text:" << adtext << std::endl;
		//Load a few images from files

		BulkProcessor engine;
		engine.start(input, outputdir, inputimagedir);
		return 0;
	}

}

