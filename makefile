APP = gl-cairo-simple
INCPATH = -I/Library/Frameworks/SDL2.framework/Headers/ -I/usr/include/cairo/
CC = gcc

CFLAGS = -Wall -c -g -std=c99 \
	 `sdl-config --cflags` \
	 `pkg-config --cflags cairo` $(INCPATH)

LDFLAGS = `sdl-config --libs` \
	  `pkg-config --libs cairo` \
	  -lGL -lGLU -lm

SRCS = main.cpp

OBJS = $(SRCS:.c=.o)

all: $(APP)

.c.o:
	$(CC) $(CFLAGS) -c $< -o $@

# it is important that $(OBJS) stands _before_ $(LDFLAGS)
$(APP):	$(OBJS)
	$(CC) $(OBJS) $(LDFLAGS) -o$(APP)  

clean:
	rm -f *.o  *~ $(APP) result.png

