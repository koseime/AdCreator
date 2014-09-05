package com.kosei.dropwizard.adcreator.core;

import com.kosei.adcreator.api.AdPreviewCreator;
import com.kosei.adcreator.api.PreviewInfo;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by lanceriedel on 9/4/14.
 */
public class AdPreviewCreatorClient {
    public ByteBuffer generate(String productImageFile, String logoImageFile) throws Exception {
        try {
            String backgroundImageFile = "invalid";
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

            TProtocol protocol = new TBinaryProtocol(transport);
            AdPreviewCreator.Client client = new AdPreviewCreator.Client(protocol);

            productImage.rewind();
            backgroundImage.rewind();
            logoImage.rewind();
            PreviewInfo previewInfo = new PreviewInfo(productImage, backgroundImage, logoImage, adLayoutJsonString,
                    title, copy);
            ByteBuffer output = client.createPreview(previewInfo);
            transport.close();

            return output;
        } catch (TException x) {
            x.printStackTrace();
        }
        return null;
    }

    private static void perform(AdPreviewCreator.Client client) throws TException {
        client.ping();
    }
}