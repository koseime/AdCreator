package com.kosei.dropwizard.adcreator.core;

import com.kosei.adcreator.api.AdPreviewCreator;
import com.kosei.adcreator.api.PreviewInfo;
import io.dropwizard.jackson.Jackson;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by lanceriedel on 9/4/14.
 */
public class AdPreviewCreatorClient {
    public ByteBuffer generate(String headerText, String descriptionText, String productImageFile, String logoImageFile,
                               String headerFont, int headerFontSize, int headerFontWeight, String headerFontColor,
                               String descriptionFont, int descriptionFontSize, int descriptionFontWeight, String descriptionFontColor,
                               String backgroundColor) throws Exception {
        try {
            String backgroundImageFile = "invalid";
            String sample = "{\"name\":\"ad2\", \"template_id\":\"template_1\", " +
                    "\"background_filename\":\"null\", \"logo_filename\":\"dd.jpg\"," +
                    "\"title_font\":{\"name\":\"micross\", \"size\":20, \"weight\":700}," +
                    "\"description_font\":{\"name\":\"CreatiCredad\", \"size\":12, \"weight\":400}}\n";

            String title = "Here is Header";
            String copy = "Here is Copy";

            if (headerText!=null) title = headerText;
            if (descriptionText!=null) copy = descriptionText;

            if (backgroundColor==null || backgroundColor.isEmpty()) backgroundColor = "white";
            if (headerFontColor==null || headerFontColor.isEmpty()) headerFontColor = "black";
            if (descriptionFontColor==null || descriptionFontColor.isEmpty()) descriptionFontColor = "black";





            AdLayout layout = new AdLayout();
            layout.background_color = backgroundColor;
            layout.background_filename="null";
            layout.name="ad2";
            layout.template_id="template_1";
            layout.background_filename = "null" ;
            layout.logo_filename = "dd.jpg";
            layout.title_font = new FontHolder();
            layout.title_font.name = headerFont;
            layout.title_font.size = headerFontSize;
            layout.title_font.weight = headerFontWeight;
            layout.title_font.color = headerFontColor;

            layout.description_font = new FontHolder();
            layout.description_font.name = descriptionFont;
            layout.description_font.size = descriptionFontSize;
            layout.description_font.weight = descriptionFontWeight;
            layout.description_font.color = descriptionFontColor;



            String adLayoutJsonString = Jackson.newObjectMapper().writeValueAsString(layout);

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
                productImage = ByteBuffer.allocate((int) fSize);
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
                    title, copy, backgroundColor);
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

    private class AdLayout {
        public String name;
        public String template_id;
        public FontHolder title_font;
        public FontHolder description_font;
        public String background_filename;
        public String logo_filename;
        public String background_color;

    }

    private class FontHolder {
        public String name;
        public int size;
        public int weight;
        public String color;
    }
}