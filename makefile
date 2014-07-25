APP = kosei-ad-creator
CC = g++

ACPROTO = ../AdCreatorWorkflow/build/generated-sources-cpp/
IMAGEMAGICK = /home/lanceriedel/tools/ImageMagick-6.8.9-5

export PKG_CONFIG_PATH=$(IMAGEMAGICK)/Magick++/lib/:$(IMAGEMAGICK)/wand:$(IMAGEMAGICK)/magick

 ifeq ($(shell uname),Darwin)
   OSARCH=osx
   INCPATH += -I. -I/usr/include/cairo/ -I/usr/include/protobuf -I$(ACPROTO) \
    -I/usr/local/include/ImageMagick-6/magick  -I/usr/local/include/ImageMagick-6
 else
   OSARCH=linux
   INCPATH = -I/Library/Frameworks/SDL2.framework/Headers/ -I/usr/include/cairo/ -I/usr/include/protobuf -I$(ACPROTO)
 endif

 ifeq ($(OSARCH),osx)
    CFLAGS = -Wall -c -g -std=c++0x \
    	 `pkg-config --cflags protobuf` \
    	 `Magick++-config --cxxflags --cppflags` \
    	  $(INCPATH)
    LDFLAGS = -mmacosx-version-min=10.8  -ljpeg -framework OpenGL \
            `Magick++-config --ldflags --libs` \
              -lm -lpthread `pkg-config --libs protobuf` \
              -lpthread -lcrypto -lssl -lc++ \
              -ljpeg -lpng -lfreetype -lbz2 -lfontconfig \
              -lpthread -lcrypto -lssl -lc++                         
else
    CFLAGS = -Wall -c -g -std=c99 \
    	 `pkg-config --cflags protobuf` $(INCPATH)
    LDFLAGS = -L /usr/lib64 -Wl,-Bstatic -ljpeg \
          -Wl,-Bdynamic -lGL -lGLU -lpthread -lcrypto -lssl \
          -lm -lpthread `pkg-config --libs cairo` `pkg-config --cflags protobuf`
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

