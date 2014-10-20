/*
 * AdCreatorTest.cpp
 *
 *  Created on: Sep 26, 2014
 *      Author: chantat
 */

#define BOOST_TEST_DYN_LINK
#define BOOST_TEST_MAIN

#include "../ImageMagickLayoutEngine.h"

#include <string>
#include <vector>
#include <fstream>

#include <boost/test/unit_test.hpp>

using namespace std;

BOOST_AUTO_TEST_CASE( createTest )
{
	const string image_file_path = "test/resources/Space-Rocket-Emoji.png";
	const string title = "Foo Foo Foo";
	const string copy = "Bar Bar Bar";

	ifstream fs(image_file_path.c_str());
	const string productBlob((istreambuf_iterator<char>(fs)), istreambuf_iterator<char>());

	ImageMagickLayoutEngine engine;
	engine.importResources("test/resources/layout_resource.tar.gz");

	vector<pair<string, string> > generatedAds;
	engine.createAllLayouts(productBlob, title, copy, &generatedAds);

	BOOST_CHECK_EQUAL(generatedAds.size(), 1);
	BOOST_CHECK(generatedAds[0].second.size() > 0);
}


BOOST_AUTO_TEST_CASE( createTestEmbedded )
{
	const string image_file_path = "test/resources/Space-Rocket-Emoji.png";
	const string title = "Foo Foo Foo";
	const string copy = "Bar Bar Bar";

	ifstream fs(image_file_path.c_str());
	const string productBlob((istreambuf_iterator<char>(fs)), istreambuf_iterator<char>());

	ImageMagickLayoutEngine engine;
	engine.importResources("test/resources/embedded-layout-resource/layout_resource.tar.gz");

	vector<pair<string, string> > generatedAds;
	engine.createAllLayouts(productBlob, title, copy, &generatedAds);

	BOOST_CHECK_EQUAL(generatedAds.size(), 1);
	BOOST_CHECK(generatedAds[0].second.size() > 0);
}


