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

#include "ImageMagickLayoutEngine.h"
#include "AdComponentsMessages.pb.h"

using namespace std;

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

		// Generate Ad
		ImageMagickLayoutEngine engine;
		size_t output_image_size;
		char* output_image = engine.createToBuffer(
				(const com::kosei::proto::AdComponents*)&adComponents,
				(const char*)adComponents.description().c_str(),
				&output_image_size);

		adComponents.set_generatedjpgad((void *)output_image, output_image_size);
		delete[] output_image;

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
