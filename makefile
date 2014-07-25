APP = kosei-ad-creator
CC = g++

ACPROTO = ../AdCreatorWorkflow/build/generated-sources-cpp/

 ifeq ($(shell uname),Darwin)
   OSARCH=osx
   INCPATH += -I. -I/usr/include/protobuf -I$(ACPROTO) \
    -I/usr/local/include/ImageMagick-6/magick  -I/usr/local/include/ImageMagick-6
 else
   OSARCH=linux
   INCPATH = -I. -I/usr/include/protobuf -I$(ACPROTO) \
     -I/usr/local/include/ImageMagick-6/magick  -I/usr/local/include/ImageMagick-6
   
 endif

 ifeq ($(OSARCH),osx)
    CFLAGS = -Wall -c -g -std=c++0x \
    	 `pkg-config --cflags protobuf` \
    	 `Magick++-config --cxxflags --cppflags` \
    	  $(INCPATH)
    LDFLAGS = -mmacosx-version-min=10.8  -ljpeg \
            `Magick++-config --ldflags --libs` \
              -lm -lpthread `pkg-config --libs protobuf` \
              -lpthread -lcrypto -lssl -lc++ \
              -ljpeg -lpng -lfreetype -lbz2 -lfontconfig \
              -lpthread -lcrypto -lssl -lc++                         
else
    CFLAGS = -Wall -c -g -std=c99 \
    	 `pkg-config --cflags protobuf` $(INCPATH)
    LDFLAGS = -L /usr/lib64 -Wl,-Bstatic -ljpeg \
    		/usr/local/lib/libMagick++-6.Q16.a \
        	/usr/local/lib/libMagickWand-6.Q16.a \
    		/usr/local/lib/libMagickCore-6.Q16.a \
          -Wl,-Bdynamic -lGL -lGLU -lpthread -lcrypto -lssl \
          	-lm -lpthread  `pkg-config --cflags protobuf`
 endif



PROTOSRCS = $(ACPROTO)/AdComponentsMessages.pb.cc
SRCS =  ImageMagickLayoutEngine.cpp  BulkProcessor.cpp FileProcessor.cpp AdCreatorCLI.cpp

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

