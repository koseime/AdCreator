/*
 * hadoop pipes -Dhadoop.pipes.java.recordreader=true -Dhadoop.pipes.java.recordwriter=false -input test/input.seq -output output -inputformat org.apache.hadoop.mapred.SequenceFileInputFormat -program bin/ad-creator-test
 *
  --LANCE"S WORKING (NOTE USING makefile.pipes)
 * hadoop pipes -Dhadoop.pipes.java.recordreader=true -Dhadoop.pipes.java.recordwriter=true -files images/320x50Border.png -input test/input-byteswritable.seq -output output9 -inputformat org.apache.hadoop.mapred.SequenceFileInputFormat -program bin/ad-creator-test
 */
// .cpp
#include "fstream"
#include "algorithm"
#include "limits"
#include "string"

#include  "stdint.h"  // <--- to prevent uint64_t errors! 

#include "hadoop/Pipes.hh"
#include "hadoop/TemplateFactory.hh"
#include "hadoop/StringUtils.hh"
#include "ImageMagickLayoutEngine.h"
#include "AdComponentsMessages.pb.h"


using namespace std;

class AdCreatorTestMapper : public HadoopPipes::Mapper {
public:
  int counter;

  // constructor: does nothing
  AdCreatorTestMapper( HadoopPipes::TaskContext& context ) {
	  counter = 0;
	  std::ofstream out("/tmp/DEBUG" + std::to_string(counter) + ".bin");
          out << "CONSTRUCTOR...";
          out.close();
  }

  // map function: receives a line, outputs (word,"1")
  // to reducer.
  void map( HadoopPipes::MapContext& context ) {
	  counter++;
    //--- get line of text ---
    string line = context.getInputValue();

    std::ofstream out("/tmp/output" + std::to_string(counter) + ".bin");
                    out << line;
    out << "||||" << std::endl;
    out << std::endl;

    com::kosei::proto::AdComponents adComponents;
    if (adComponents.ParseFromString(line) == false) {
    	out << std::endl;
    	out << " FAILED " << std::endl;
    }
   out << std::endl;
   out << "DEBUG:" <<std::endl;
   out << adComponents.DebugString() << std::endl;
   out << "__________________" << std::endl;
   out << adComponents.id() << std::endl;
   out << "xxxxxxxxxx" << std::endl;

   ImageMagickLayoutEngine engine;
   const char* output = NULL;
   engine.create((const com::kosei::proto::AdComponents*)&adComponents, (const char*) adComponents.description().c_str (),  output);


    out.close();

    int size = adComponents.ByteSize();
    char *buffer = new char[size];
    adComponents.SerializeToArray(buffer, size);
    std::string empty = "";
    context.emit(context.getInputKey(), std::string(buffer, size));
    delete[] buffer;
  }
};

class AdCreatorTestReducer : public HadoopPipes::Reducer {
public:
  // constructor: does nothing
  AdCreatorTestReducer(HadoopPipes::TaskContext& context) {
  }

  // reduce function
  void reduce( HadoopPipes::ReduceContext& context ) {

  }
};

int main(int argc, char *argv[]) {
  return HadoopPipes::runTask(HadoopPipes::TemplateFactory< 
                                                      AdCreatorTestMapper,
                              AdCreatorTestReducer >() );
}
