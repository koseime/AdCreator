// This autogenerated skeleton file illustrates how to build a server.
// You should copy it to another filename to avoid overwriting it.

#include "gen-cpp/AdPreviewCreator.h"
#include <thrift/protocol/TBinaryProtocol.h>
#include <thrift/server/TSimpleServer.h>
#include <thrift/transport/TServerSocket.h>
#include <thrift/transport/TBufferTransports.h>

#include "../ImageMagickLayoutEngine.h"

using namespace ::apache::thrift;
using namespace ::apache::thrift::protocol;
using namespace ::apache::thrift::transport;
using namespace ::apache::thrift::server;

using boost::shared_ptr;

class AdPreviewCreatorHandler : virtual public AdPreviewCreatorIf {
 public:
  ImageMagickLayoutEngine engine;

  AdPreviewCreatorHandler() {
  }

  void ping() {
    printf("ping\n");
  }

  void createPreview(std::string& _return, const PreviewInfo& previewInfo) {
    printf("createPreview\n");
    engine.create(previewInfo.productBlob, previewInfo.backgroundBlob, previewInfo.logoBlob,
    		AdLayoutEntry(previewInfo.adEntryJsonString), previewInfo.title, previewInfo.copy, &_return);
  }

};

int main(int argc, char **argv) {
  int port = 9090;
  boost::shared_ptr<AdPreviewCreatorHandler> handler(new AdPreviewCreatorHandler());
  boost::shared_ptr<TProcessor> processor(new AdPreviewCreatorProcessor(handler));
  boost::shared_ptr<TServerTransport> serverTransport(new TServerSocket(port));
  boost::shared_ptr<TTransportFactory> transportFactory(new TBufferedTransportFactory());
  boost::shared_ptr<TProtocolFactory> protocolFactory(new TBinaryProtocolFactory());

  TSimpleServer server(processor, serverTransport, transportFactory, protocolFactory);
  server.serve();
  return 0;
}
