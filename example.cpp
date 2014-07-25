#include <Magick++.h>
#include <iostream>

#include <fstream>
#include <stdio.h>

#include <stdlib.h>
#include <string.h>


using namespace std;
using namespace Magick;


/*
 * examples;: http://webcache.googleusercontent.com/search?q=cache:Pw_ba68uO1QJ:ftp://ftp.mpe.mpg.de/pub/ImageMagick/ImageMagick-5.3.3/Magick%2B%2B/demo/demo.cpp+&cd=2&hl=en&ct=clnk&gl=us
 *
 *  from data example:   -- const void* data_, size_t length_ --
 * Blob blob( data, length );
Image image;
image.read( blob);


 */
int main(int argc,char **argv)
{
	InitializeMagick(*argv);

	// Construct the image object. Seperating image construction from the
	// the read operation ensures that a failure to read the image file
	// doesn't render the image object useless.
	Image image;


	try {
		// Read a file into image object

		// Load file content into byte array
		std::ifstream fl("gif-sample.gif");
		fl.seekg( 0, std::ios::end );
		size_t len = fl.tellg();
		char *product_image = new char[len];
		fl.seekg(0, std::ios::beg);
		fl.read(product_image, len);
		fl.close();

		Blob blob(product_image, len);
		Image image( blob );
		//image.magick( "GIF" ); // Not sure when you need this??

		Image image2( blob );
		image2.scale( Geometry(50,50, 0, 0) );

		//Test overlay/composite
		image.composite(image2, 70, 70,OverCompositeOp);

		//write composited image
		image.write( "composite.gif" );
		image.write( "composite.jpg" ); //interesting, this is all it takes to write as a jpg - auto recognition of file extension


		// Crop the image to specified size (width, height, xOffset, yOffset)
		image.crop( Geometry(100,100, 100, 100) );
		image.write( "x.gif" );

		//as PNG
		image.magick("png");
		// Write the image to a file
		image.write( "x.png" );

		//write ad jpg
		image.magick("jpg");
		image.write( "x.jpg" );


	}
	catch( Exception &error_ )
	{
		cout << "Caught exception: " << error_.what() << endl;
		return 1;
	}
	return 0;
}
