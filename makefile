APP = kosei-ad-creator
CC = gcc






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
    	 `pkg-config --cflags cairo` $(INCPATH)
    LDFLAGS = -mmacosx-version-min=10.8   -lstdc++.6 -lSDL -ljpeg -framework OpenGL \
              -lm -lpthread `pkg-config --libs cairo`
else
    CFLAGS = -Wall -c -g -std=c99 \
    	 `sdl-config --cflags` \
    	 `pkg-config --cflags cairo` $(INCPATH)
    LDFLAGS = -L /usr/lib64 -Wl,-Bstatic -lSDL -ljpeg\
          -Wl,-Bdynamic -lGL -lGLU \
          -lm -lpthread `pkg-config --libs cairo`

 endif



SRCS =  LayoutEngine.cpp JpegUtil.cpp BulkProcessor.cpp FileProcessor.cpp AdCreatorCLI.cpp

OBJS = $(SRCS:.c=.o)

all: $(APP)

.c.o:
	$(CC) $(CFLAGS) -c $< -o $@

# it is important that $(OBJS) stands _before_ $(LDFLAGS)
$(APP):	$(OBJS)
	$(CC) $(OBJS) $(LDFLAGS) -o$(APP)  

clean:
	rm -f *.o  *~ $(APP) output/*.*

