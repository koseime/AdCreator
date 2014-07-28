APP = ad-creator-test-cli
CC = g++
HADOOP_SRC_DIR = $(HADOOP_SRC)
ACPROTO=../AdCreatorWorkflow/build/generated-sources-cpp/
export PKG_CONFIG_PATH=$PKG_CONFIG_PATH:/usr/local/lib/pkgconfig



 ifeq ($(shell uname),Darwin)
   OSARCH=osx
   INCPATH += -I$(ACPROTO) -I. -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/pipes/api \
   -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/utils/api  -I/usr/include/protobuf  \
   -I/usr/local/include/ImageMagick-6/magick  -I/usr/local/include/ImageMagick-6
 else
   OSARCH=linux
   INCPATH = -I$(ACPROTO) -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/pipes/api \
   -I/usr/local/include/ImageMagick-6/magick  -I/usr/local/include/ImageMagick-6 \
    -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/utils/api   -I/usr/include/protobuf 
 endif

 ifeq ($(OSARCH),osx)
    CFLAGS = -Wall -c -g -std=c++0x \
    	 `pkg-config --cflags protobuf` $(INCPATH)
    LDFLAGS = -mmacosx-version-min=10.8  -ljpeg \
              -lm -lpthread \
              `pkg-config --libs protobuf` \
              $(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/target/native/libhadooppipes.a \
              $(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/target/native/libhadooputils.a \
              `Magick++-config --ldflags --libs` \
       		  -ljpeg -lpng -lfreetype -lbz2 -lfontconfig -lpthread -lcrypto -lssl -lc++
else
    CFLAGS = -Wall -c -g $(INCPATH) `pkg-config --cflags protobuf` \
    		-I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/pipes/api \
    		-I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/utils/api 
    LDFLAGS = -L /usr/lib64  \
            -Wl,-Bstatic -L $(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/target/native -lhadooppipes -lhadooputils -ljpeg \
		    /usr/local/lib/libMagick++-6.Q16.a \
        	/usr/local/lib/libMagickWand-6.Q16.a \
    		/usr/local/lib/libMagickCore-6.Q16.a \
    		/usr/lib64/libjpeg.a \
    		-L/usr/local/lib -lprotobuf \
          -Wl,-Bdynamic -lpthread -lcrypto -lssl \
           -lm -lpthread -lpng -lX11 -lgomp \
           -lfreetype -lbz2 -lfontconfig -lXext \
    		
 endif


PROTOSRCS = $(ACPROTO)/AdComponentsMessages.pb.cc
#SRCS =  AdCreatorCLI.cpp

SRCS =  ImageMagickLayoutEngine.cpp BulkProcessor.cpp FileProcessor.cpp AdCreatorCLI.cpp



PROTOOBJS = $(PROTOSRCS:.cc=.o)
OBJS = $(SRCS:.cpp=.o) $(PROTOOBJS)

all: $(APP)

.cc.o:
	$(CC) $(CFLAGS) -c $< -o $@
	
.cpp.o:
	$(CC) $(CFLAGS) -c $< -o $@


# it is important that $(OBJS) stands _before_ $(LDFLAGS)
$(APP):	$(OBJS)
	$(CC) $(OBJS) $(LDFLAGS) -o$(APP)

clean:
	rm -f $(ACPROTO)*.o *.o  *~ $(APP) output/*.*

APP = ad-creator-test-cli
CC = g++
HADOOP_SRC_DIR = $(HADOOP_SRC)
ACPROTO=../AdCreatorWorkflow/build/generated-sources-cpp/
export PKG_CONFIG_PATH=$PKG_CONFIG_PATH:/usr/local/lib/pkgconfig



 ifeq ($(shell uname),Darwin)
   OSARCH=osx
   INCPATH += -I$(ACPROTO) -I. -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/pipes/api \
   -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/utils/api  -I/usr/include/protobuf  \
   -I/usr/local/include/ImageMagick-6/magick  -I/usr/local/include/ImageMagick-6
 else
   OSARCH=linux
   INCPATH = -I$(ACPROTO) -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/pipes/api \
   -I/usr/local/include/ImageMagick-6/magick  -I/usr/local/include/ImageMagick-6 \
    -I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/utils/api   -I/usr/include/protobuf 
 endif

 ifeq ($(OSARCH),osx)
    CFLAGS = -Wall -c -g -std=c++0x \
    	 `pkg-config --cflags protobuf` $(INCPATH)
    LDFLAGS = -mmacosx-version-min=10.8  -ljpeg \
              -lm -lpthread \
              `pkg-config --libs protobuf` \
              $(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/target/native/libhadooppipes.a \
              $(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/target/native/libhadooputils.a \
              `Magick++-config --ldflags --libs` \
       		  -ljpeg -lpng -lfreetype -lbz2 -lfontconfig -lpthread -lcrypto -lssl -lc++
else
    CFLAGS = -Wall -c -g $(INCPATH) `pkg-config --cflags protobuf` \
    		-I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/pipes/api \
    		-I$(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/src/main/native/utils/api 
    LDFLAGS = -L /usr/lib64  \
            -Wl,-Bstatic -L $(HADOOP_SRC_DIR)/hadoop-tools/hadoop-pipes/target/native -lhadooppipes -lhadooputils -ljpeg \
		    /usr/local/lib/libMagick++-6.Q16.a \
        	/usr/local/lib/libMagickWand-6.Q16.a \
    		/usr/local/lib/libMagickCore-6.Q16.a \
    		/usr/lib64/libjpeg.a \
    		-L/usr/local/lib -lprotobuf \
          -Wl,-Bdynamic -lpthread -lcrypto -lssl \
           -lm -lpthread -lpng -lX11 -lgomp \
           -lfreetype -lbz2 -lfontconfig -lXext \
    		
 endif


PROTOSRCS = $(ACPROTO)/AdComponentsMessages.pb.cc
#SRCS =  AdCreatorCLI.cpp

SRCS =  ImageMagickLayoutEngine.cpp BulkProcessor.cpp FileProcessor.cpp AdCreatorCLI.cpp



PROTOOBJS = $(PROTOSRCS:.cc=.o)
OBJS = $(SRCS:.cpp=.o) $(PROTOOBJS)

all: $(APP)

.cc.o:
	$(CC) $(CFLAGS) -c $< -o $@
	
.cpp.o:
	$(CC) $(CFLAGS) -c $< -o $@


# it is important that $(OBJS) stands _before_ $(LDFLAGS)
$(APP):	$(OBJS)
	$(CC) $(OBJS) $(LDFLAGS) -o$(APP)

clean:
	rm -f $(ACPROTO)*.o *.o  *~ $(APP) output/*.*

