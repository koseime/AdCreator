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

#include "hadoop/Pipes.hh"
#include "hadoop/TemplateFactory.hh"
#include "Magick++.h"

#include "ImageMagickLayoutEngine.h"
#include "AdComponentsMessages.pb.h"

using namespace std;
using namespace Magick;

class AdCreatorMapper : public HadoopPipes::Mapper {
public:
	// constructor: does nothing
	AdCreatorMapper(HadoopPipes::TaskContext& context) {
	}

	// map function
	void map( HadoopPipes::MapContext& context ) {
		// Parse input to protobuf format
		string line = context.getInputValue();
		com::kosei::proto::AdComponents adComponents;
		adComponents.ParseFromString(line);

		// Initialize ImageMagickLayoutEngine
		ImageMagickLayoutEngine engine;
		engine.importResources(context.getJobConf()->get("layout.resource.tar"));

		vector<pair<string, Blob> > generatedJpgAds;
		engine.createFromLayouts((const com::kosei::proto::AdComponents*)&adComponents,
				generatedJpgAds);

		// Add to AdComponents protobuf
		for (int i = 0; i < generatedJpgAds.size(); i++) {
			com::kosei::proto::AdComponents_Ad* ad = adComponents.add_generatedads();
			ad->set_adjpg(generatedJpgAds[i].second.data(), generatedJpgAds[i].second.length());
			ad->set_layoutname(generatedJpgAds[i].first);
		}

		// Serialize and write output
		int size = adComponents.ByteSize();
		char *buffer = new char[size];
		adComponents.SerializeToArray(buffer, size);
		context.emit(context.getInputKey(), string(buffer, size));
		delete[] buffer;
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
