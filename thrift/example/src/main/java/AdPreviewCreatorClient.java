/**
 * Created by chantat on 9/1/14.
 */

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class AdPreviewCreatorClient {
    public static void main(String [] args) {
        try {
            String productImageFile = "vacuum1.jpg";
            String backgroundImageFile = "invalid";
            String logoImageFile = "dd.jpg";
            String adLayoutJsonString = "{\"name\":\"ad2\", \"template_id\":\"template_1\", " +
                    "\"background_filename\":\"null\", \"logo_filename\":\"dd.jpg\"," +
                    "\"title_font\":{\"name\":\"micross\", \"size\":20, \"weight\":700}," +
                    "\"description_font\":{\"name\":\"CreatiCredad\", \"size\":12, \"weight\":400}}\n";
            String title = "Here is Header";
            String copy = "Here is Copy";

            TTransport transport;

            transport = new TSocket("localhost", 9090);
            transport.open();

            ByteBuffer productImage = ByteBuffer.allocate(0);
            ByteBuffer backgroundImage = ByteBuffer.allocate(0);
            ByteBuffer logoImage = ByteBuffer.allocate(0);
            try {
                FileInputStream fIn = new FileInputStream(productImageFile);
                FileChannel fChan = fIn.getChannel();
                long fSize = fChan.size();
                productImage = ByteBuffer.allocate((int)fSize);
                fChan.read(productImage);
                fChan.close();
                fIn.close();

                if (!backgroundImageFile.equals("invalid")) {
                    fIn = new FileInputStream(backgroundImageFile);
                    fChan = fIn.getChannel();
                    fSize = fChan.size();
                    backgroundImage = ByteBuffer.allocate((int) fSize);
                    fChan.read(backgroundImage);
                    fChan.close();
                    fIn.close();
                }

                fIn = new FileInputStream(logoImageFile);
                fChan = fIn.getChannel();
                fSize = fChan.size();
                logoImage = ByteBuffer.allocate((int) fSize);
                fChan.read(logoImage);
                fChan.close();
                fIn.close();
                
            } catch (Exception exc) {
                System.out.println(exc);
                System.exit(1);
            }

            TProtocol protocol = new  TBinaryProtocol(transport);
            AdPreviewCreator.Client client = new AdPreviewCreator.Client(protocol);

            productImage.rewind();
            backgroundImage.rewind();
            logoImage.rewind();
            PreviewInfo previewInfo = new PreviewInfo(productImage, backgroundImage, logoImage, adLayoutJsonString,
                    title, copy);
            ByteBuffer output = client.createPreview(previewInfo);

            try {
                FileChannel out = new FileOutputStream("output.jpg").getChannel();
                out.write(output);
                out.close();
            } catch (Exception e) {
                System.out.println(e);
                System.exit(1);
            }
            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }

    private static void perform(AdPreviewCreator.Client client) throws TException {
        client.ping();
    }
}