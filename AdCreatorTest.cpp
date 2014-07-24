/*
 * hadoop pipes -Dhadoop.pipes.java.recordreader=true -Dhadoop.pipes.java.recordwriter=false -input test/input.seq -output output -inputformat org.apache.hadoop.mapred.SequenceFileInputFormat -program bin/ad-creator-test
 *
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

using namespace std;

class AdCreatorTestMapper : public HadoopPipes::Mapper {
public:
  int counter;

  // constructor: does nothing
  AdCreatorTestMapper( HadoopPipes::TaskContext& context ) {
	  counter = 0;
  }

  // map function: receives a line, outputs (word,"1")
  // to reducer.
  void map( HadoopPipes::MapContext& context ) {
	  counter++;
    //--- get line of text ---
    string line = context.getInputValue();

    std::ofstream out("output" + std::to_string(counter) + ".bin");
    out << line;
    out.close();

  }
};

class AdCreatorTestReducer : public HadoopPipes::Reducer {
public:
  // constructor: does nothing
  AdCreatorTestReducer(HadoopPipes::TaskContext& context) {
  }

  // reduce function
  void reduce( HadoopPipes::ReduceContext& context ) {
    int count = 0;

    //--- get all tuples with the same key, and count their numbers ---
    while ( context.nextValue() ) {
      count += HadoopUtils::toInt( context.getInputValue() );
    }

    //--- emit (word, count) ---
    context.emit(context.getInputKey(), HadoopUtils::toString( count ));
  }
};

int main(int argc, char *argv[]) {
  return HadoopPipes::runTask(HadoopPipes::TemplateFactory< 
                                                      AdCreatorTestMapper,
                              AdCreatorTestReducer >() );
}
