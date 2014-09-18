APPMR = ad-creator
APPCLI = ad-creator-client
CC = g++

ACPROTO = ./ad_creator_workflow
ACPROTO_VERSION = 0.4
export PKG_CONFIG_PATH=$PKG_CONFIG_PATH:/usr/local/lib/pkgconfig
HADOOP_SRC_DIR = $(HADOOP_SRC)


 ifeq ($(shell uname),Darwin)
   OSARCH=osx
   INCPATH += -I. -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/pipes/api \
   -I/usr/local/include/ImageMagick-6/magick  -I/usr/local/include/ImageMagick-6 \
   -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/utils/api \
   -I/usr/local/include -I/usr/include/protobuf -I$(ACPROTO)
 else
   OSARCH=linux
   INCPATH = -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/pipes/api \
   -I/usr/local/include/ImageMagick-6/magick  -I/usr/local/include/ImageMagick-6 \
    -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/utils/api   -I/usr/include/protobuf -I$(ACPROTO)
 endif

 ifeq ($(OSARCH),osx)
    CFLAGS = -Wall -O3 -c -g -std=c++0x $(INCPATH)
    LDFLAGS = -mmacosx-version-min=10.8 \
              -lm -lpthread \
              `pkg-config --libs protobuf` \
              `Magick++-config --ldflags --libs` \
              $(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/target/native/libhadooppipes.a \
              $(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/target/native/libhadooputils.a \
              /usr/local/lib/libarchive.a -lz -lbz2 \
              -lpthread -lcrypto -lssl -lc++
else
    CFLAGS = -Wall -O3 -c -g $(INCPATH)
    LDFLAGS = -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/pipes/api \
              -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/utils/api -L /usr/lib64  \
              -Wl,-Bstatic -L $(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/target/native/ -lhadooppipes -lhadooputils \
              /usr/local/lib/libprotobuf.a \
              /usr/local/lib/libMagick++-6.Q16.a \
              /usr/local/lib/libMagickWand-6.Q16.a \
              /usr/local/lib/libMagickCore-6.Q16.a \
              /usr/local/lib/libarchive.a \
              -Wl,-Bdynamic -lfontconfig -lfreetype -lXext -llcms -lgomp -lX11 -lbz2 -ltiff -lpng -lpthread -lcrypto -lssl \
              -lm -lpthread
 endif



PROTOSRCS = $(ACPROTO)/AdComponentsMessages.pb.cc
SRCS =  ImageMagickLayoutEngine.cpp LayoutEngineManager.cpp AdLayoutEntry.cpp AdCreator.cpp jsonxx.cpp
CLISRCS = ImageMagickLayoutEngine.cpp LayoutEngineManager.cpp AdLayoutEntry.cpp BulkProcessor.cpp FileProcessor.cpp AdCreatorCLI.cpp jsonxx.cpp

PROTOOBJS = $(PROTOSRCS:.cc=.o)
OBJS = $(SRCS:.cpp=.o) $(PROTOOBJS)
CLIOBJS = $(CLISRCS:.cpp=.o) $(PROTOOBJS)

all: ad_creator_workflow $(APPMR) $(APPCLI)

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

ad_creator_workflow:
	mkdir -p $(ACPROTO); \
	cd $(ACPROTO); \
	wget http://repository-kosei.forge.cloudbees.com/release/com/kosei/jobs/ad-creator-workflow/$(ACPROTO_VERSION)/ad-creator-workflow-$(ACPROTO_VERSION)-sources.jar; \
	jar xf ad-creator-workflow-$(ACPROTO_VERSION)-sources.jar

check-env:
ifndef HADOOP_SRC
    $(error HADOOP_SRC is undefined)
endif

