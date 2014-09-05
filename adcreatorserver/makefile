APP = ad-preview-creator-server
CC = g++
VPATH = ../api/build/generated-src/thrift/main

export PKG_CONFIG_PATH=$PKG_CONFIG_PATH:/usr/local/lib/pkgconfig

 ifeq ($(shell uname),Darwin)
   OSARCH=osx
   INCPATH += -I. -I/usr/local/include/ImageMagick-6/magick  -I/usr/local/include/ImageMagick-6 \
   -I/usr/local/include -I/usr/include/boost -I/usr/local/include/thrift -I$(VPATH) 
 else
   OSARCH=linux
   INCPATH = -I/usr/local/include/ImageMagick-6/magick  -I/usr/local/include/ImageMagick-6 \
   -I/usr/local/include -I/usr/include/boost -I/usr/local/include/thrift -I$(VPATH)
 endif

 ifeq ($(OSARCH),osx)
    CFLAGS = -Wall -c -g -std=c++0x $(INCPATH)
    LDFLAGS = -mmacosx-version-min=10.8 \
              -lm -lpthread -lthrift \
              `Magick++-config --ldflags --libs` \
              /usr/local/lib/libarchive.a -lz -lbz2 \
              -lpthread -lcrypto -lssl -lc++
else
    CFLAGS = -Wall -c -g $(INCPATH)
    COMMON_DEF = -DHAVE_INTTYPES_H -DHAVE_NETINET_IN_H
    LDFLAGS = -L /usr/lib64  \
              -Wl,-Bstatic -lthrift \
              /usr/local/lib/libMagick++-6.Q16.a \
              /usr/local/lib/libMagickWand-6.Q16.a \
              /usr/local/lib/libMagickCore-6.Q16.a \
              /usr/local/lib/libarchive.a \
              -Wl,-Bdynamic -lfontconfig -lfreetype -lXext -llcms -lgomp -lX11 -lbz2 -ltiff -lpng -lpthread -lcrypto -lssl \
              -lm -lpthread
 endif

SRCS =  $(VPATH)/AdPreviewCreator.cpp $(VPATH)/AdPreviewCreator_types.cpp $(VPATH)/AdPreviewCreator_constants.cpp AdPreviewCreator_server.cpp

OBJS = $(SRCS:.cpp=.o)
AC_OBJS = ../native/AdLayoutEntry.o ../native/ImageMagickLayoutEngine.o ../native/LayoutEngineManager.o ../native/jsonxx.o

all: $(APP)

.cpp.o:
	$(CC) $(COMMON_DEF) $(CFLAGS) -c $< -o $@

# it is important that $(OBJS) stands _before_ $(LDFLAGS)
$(APP):	$(OBJS)
	$(CC) $(COMMON_DEF) $(OBJS) $(AC_OBJS) $(LDFLAGS) -o$(APP)  

clean:
	rm -f *.o  *~ $(APP)
