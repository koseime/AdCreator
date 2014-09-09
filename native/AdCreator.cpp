/*
 Command to run pipes
 hadoop pipes \
  -Dhadoop.pipes.java.recordreader=true \
  -Dhadoop.pipes.java.recordwriter=true \
  -Dmapred.reduce.tasks=0 \
  -files images/320x50Border.png \
  -input test/input-byteswritable.seq \
  -inputformat org.apache.hadoop.mapred.SequenceFileInputFormat \
  -output output \
  -writer org.apache.hadoop.mapred.SequenceFileOutputFormat \
  -program bin/ad-creator
*/

// .cpp
#include <string>
#include <iostream>

#include "hadoop/Pipes.hh"
#include "hadoop/TemplateFactory.hh"
#include "Magick++.h"

#include "ImageMagickLayoutEngine.h"
#include "AdComponentsMessages.pb.h"

using namespace std;
using namespace Magick;

class AdCreatorMapper : public HadoopPipes::Mapper {
public:
	ImageMagickLayoutEngine engine;

	// TODO: initializing in constructor might not be correct
	// constructor: does nothing
	AdCreatorMapper(HadoopPipes::TaskContext& context) {
		int retVal = engine.importResources(context.getJobConf()->get("layout.resource.tar"));
		if (retVal != 0) {
			cerr << "Error importing images and layouts" << endl;
			exit(-1);
		}
	}

	// map function
	void map( HadoopPipes::MapContext& context ) {
		// Parse input to protobuf format
		string line = context.getInputValue();
		com::kosei::proto::AdComponents adComponents;
		adComponents.ParseFromString(line);

		if (!isDeleted(adComponents) &&
				adComponents.status() == com::kosei::proto::AdComponents_Status_IMAGE_RETRIEVED) {
			// TODO: Ask Lance for correct values
			//adComponents.set_title("");
			//adComponents.set_description("");

			// Generate Ads
			vector<pair<string, string> > generatedJpgAds;
			engine.createAllLayouts(adComponents.productjpg(), adComponents.title(),
					adComponents.description(), &generatedJpgAds);

			// Add to AdComponents protobuf
			for (int i = 0; i < generatedJpgAds.size(); i++) {
				com::kosei::proto::AdComponents_Ad* ad = adComponents.add_generatedads();
				ad->set_adjpg(generatedJpgAds[i].second);
				ad->set_layoutname(generatedJpgAds[i].first);
			}
			adComponents.set_status(com::kosei::proto::AdComponents_Status_AD_GENERATED);
		}

		// Serialize and write output
		int size = adComponents.ByteSize();
		char *buffer = new char[size];
		adComponents.SerializeToArray(buffer, size);
		context.emit(context.getInputKey(), string(buffer, size));
		delete[] buffer;
	}

	bool isDeleted(const com::kosei::proto::AdComponents &adComponents) {
		for (int i = 0; i < adComponents.meta_size(); i++) {
			const com::kosei::proto::AdComponents_Meta &metaEntry = adComponents.meta(i);
			if (metaEntry.key().compare("deleted") == 0) {
				return true;
			}
		}
		return false;
	}
};

class AdCreatorReducer : public HadoopPipes::Reducer {
public:
	// constructor: does nothing
	AdCreatorReducer(HadoopPipes::TaskContext& context) {
	}

	// reduce function
	void reduce(HadoopPipes::ReduceContext& context) {
	}
};

int main(int argc, char *argv[]) {
	return HadoopPipes::runTask(
			HadoopPipes::TemplateFactory<AdCreatorMapper, AdCreatorReducer>());
}
