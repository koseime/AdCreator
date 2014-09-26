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
			<< "\t-t <ad title>  : single - ad title" << std::endl
			<< "\t-c <ad copy>  : single - ad copy" << std::endl
			<< "\t-r <ad result>  : single - ad result output" << std::endl
			<< std::endl << std::endl;
}

int main(int argc, char *argv[])
{
	std::string input, inputimagedir, outputdir, exectype, prodimage, title, copy, adresult;
	int opt;
	while ((opt = getopt(argc, argv, "x:i:o:p:t:c:r:g:h")) != -1) {
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
		case 't':
			title = optarg;
			break;
		case 'c':
			copy = optarg;
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
		std::cout << "Create an ad from parts - single use: " << std::endl
				<< "image: " << prodimage << std::endl
				<< "title: " << title << std::endl
				<< "copy: " << copy << std::endl;

		ImageMagickLayoutEngine engine;
		engine.importResources("images/layout_resource.tar.gz");
		return engine.create(prodimage, title, copy, adresult);
	} else if (exectype.compare("bulk") == 0) {
		BulkProcessor engine;
		engine.start(input, outputdir, inputimagedir, "images/layout_resource.tar.gz");
		return 0;
	}

}

