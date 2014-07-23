APP = kosei-ad-creator
CC = gcc

ACPROTO = ../AdCreatorWorkflow/build/generated-sources-cpp/

 ifeq ($(shell uname),Darwin)
   OSARCH=osx
   INCPATH += -I. -I/usr/include/cairo/
 else
   OSARCH=linux
   INCPATH = -I/Library/Frameworks/SDL2.framework/Headers/ -I/usr/include/cairo/
 endif

 ifeq ($(OSARCH),osx)
    CFLAGS = -Wall -c -g -std=c99 \
    	 `sdl-config --cflags` \
    	 $(INCPATH)
    LDFLAGS = -mmacosx-version-min=10.8 -std=c++0x --stdlib=libc++ -lc++ -lSDL -ljpeg -framework OpenGL \
              -lm -lpthread -L/usr/local/Cellar/cairo/1.12.16_1/lib -lcairo \
              -L/usr/local/Cellar/protobuf/2.5.0/lib/ -lprotoc -lprotobuf
else
    CFLAGS = -Wall -c -g -std=c99 \
    	 `sdl-config --cflags` \
    	 `pkg-config --cflags cairo` $(INCPATH)
    LDFLAGS = -L /usr/lib64 -Wl,-Bstatic -lSDL -ljpeg\
          -Wl,-Bdynamic -lGL -lGLU \
          -lm -lpthread `pkg-config --libs cairo`

 endif



SRCS =  LayoutEngine.cpp JpegUtil.cpp BulkProcessor.cpp FileProcessor.cpp AdCreatorCLI.cpp  $(ACPROTO)/AdComponentsMessages.pb.cc

OBJS = $(SRCS:.c=.o)

all: $(APP)

.c.o:
	$(CC) $(CFLAGS) -c $< -o $@

# it is important that $(OBJS) stands _before_ $(LDFLAGS)
$(APP):	$(OBJS)
	$(CC) $(OBJS) $(LDFLAGS) -I$(ACPROTO) -o$(APP)

clean:
	rm -f *.o  *~ $(APP) output/*.*

