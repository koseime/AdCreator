APPMR = ad-creator
APPCLI = ad-creator-client
CC = g++
HADOOP_SRC_DIR = $(HADOOP_SRC)
ACPROTO = ../AdCreatorWorkflow/build/generated-sources-cpp/
export PKG_CONFIG_PATH=$PKG_CONFIG_PATH:/usr/local/lib/pkgconfig


 ifeq ($(shell uname),Darwin)
   OSARCH=osx
   INCPATH += -I. -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/pipes/api \
   -I/usr/local/include/ImageMagick-6/magick  -I/usr/local/include/ImageMagick-6 \
   -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/utils/api \
   -I/usr/local/include \
   -I/usr/include/protobuf -I$(ACPROTO)
 else
   OSARCH=linux
   INCPATH = -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/pipes/api \
   -I/usr/local/include/ImageMagick-6/magick  -I/usr/local/include/ImageMagick-6 \
    -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/utils/api   -I/usr/include/protobuf -I$(ACPROTO)
 endif

 ifeq ($(OSARCH),osx)
    CFLAGS = -Wall -c -g -std=c++0x \
    	 `pkg-config --cflags protobuf` $(INCPATH)
    LDFLAGS = -mmacosx-version-min=10.8  -ljpeg  \
              -lm -lpthread \
              `pkg-config --libs protobuf` \
              `Magick++-config --ldflags --libs` \
              $(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/target/native/libhadooppipes.a \
              $(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/target/native/libhadooputils.a \
              /usr/local/lib/libarchive.a -lz -lbz2 \
              -lpthread -lcrypto -lssl -lc++
else
    CFLAGS = -Wall -c -g \
    	  $(INCPATH)
    LDFLAGS = -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/pipes/api \
              -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/utils/api -L /usr/lib64  \
              -Wl,-Bstatic -L /home/kosei/tools/lib/hadoop/lib/native -lhadooppipes -lhadooputils \
              /usr/lib64/libjpeg.a \
              /usr/local/lib/libMagick++-6.Q16.a \
              /usr/local/lib/libMagickWand-6.Q16.a \
              /usr/local/lib/libMagickCore-6.Q16.a \
              /usr/local/lib/libarchive.a \
              -Wl,-Bdynamic -lpthread -lcrypto -lssl \
              -lm -lpthread
 endif



PROTOSRCS = $(ACPROTO)/AdComponentsMessages.pb.cc
SRCS =  ImageMagickLayoutEngine.cpp LayoutEngineManager.cpp AdLayoutEntry.cpp AdCreator.cpp
CLISRCS = ImageMagickLayoutEngine.cpp LayoutEngineManager.cpp AdLayoutEntry.cpp BulkProcessor.cpp FileProcessor.cpp AdCreatorCLI.cpp

PROTOOBJS = $(PROTOSRCS:.cc=.o)
OBJS = $(SRCS:.cpp=.o) $(PROTOOBJS)
CLIOBJS = $(CLISRCS:.cpp=.o) $(PROTOOBJS)

all: $(APPMR) $(APPCLI)

.cc.o:
	$(CC) $(CFLAGS) -c $< -o $@
	
.cpp.o:
	$(CC) $(CFLAGS) -c $< -o $@

# it is important that $(OBJS) stands _before_ $(LDFLAGS)
$(APPMR):	$(OBJS)
	$(CC) $(OBJS) $(LDFLAGS) -o$(APPMR)  

$(APPCLI):	$(CLIOBJS)
	$(CC) $(CLIOBJS) $(LDFLAGS) -o$(APPCLI)  

clean:
	rm -f *.o  *~ $(APPMR) $(APPCLI) output/*.*

