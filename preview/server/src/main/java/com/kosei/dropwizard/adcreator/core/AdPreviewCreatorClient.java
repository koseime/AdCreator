package com.kosei.dropwizard.adcreator.core;

import com.kosei.adcreator.api.AdPreviewCreator;
import com.kosei.adcreator.api.PreviewInfo;
import io.dropwizard.jackson.Jackson;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import javax.ws.rs.QueryParam;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lanceriedel on 9/4/14.
 */
public class AdPreviewCreatorClient {
    public ByteBuffer generate(String headerText, String descriptionText, String priceText, String productImageFile, String logoImageFile, String callToActionImageFile,
                               String headerFont, int headerFontSize, int headerFontWeight, String headerFontColor,
                               String priceFont, int priceFontSize, int priceFontWeight, String priceFontColor,
                               String descriptionFont, int descriptionFontSize, int descriptionFontWeight, String descriptionFontColor,
                               String backgroundColor, String template_id,
                               int productHeight,
                               int productWidth,
                               int productOriginX,
                               int productOriginY,
                               int titleHeight,
                               int titleWidth,
                               int titleOriginX,
                               int titleOriginY,
                               int descriptionHeight,
                               int descriptionWidth,
                               int descriptionOriginX,
                               int descriptionOriginY,
                               int priceHeight,
                               int priceWidth,
                               int priceOriginX,
                               int priceOriginY,
                               int logoHeight,
                               int logoWidth,
                               int logoOriginX,
                               int logoOriginY,
                               int calltoactionHeight,
                               int calltoactionWidth,
                               int calltoactionOriginX,
                               int calltoactionOriginY,
                               int mainHeight,
                               int mainWidth,
                               String templateDefName

    ) throws Exception {
        try {
            String backgroundImageFile = "invalid";
            String sample = "{\"name\":\"ad2\", \"template_id\":\"template_1\", " +
                    "\"background_filename\":\"null\", \"logo_filename\":\"dd.jpg\"," +
                    "\"title_font\":{\"name\":\"micross\", \"size\":20, \"weight\":700}," +
                    "\"description_font\":{\"name\":\"CreatiCredad\", \"size\":12, \"weight\":400}}\n";

            String title = "Here is Header";
            String copy = "Here is Copy";
            String price = "$0.00";


            if (headerText!=null && !headerText.isEmpty()) title = headerText;
            if (descriptionText!=null && !descriptionText.isEmpty()) copy = descriptionText;
            if (priceText!=null && !priceText.isEmpty()) price = priceText;


            if (backgroundColor==null || backgroundColor.isEmpty()) backgroundColor = "white";
            if (headerFontColor==null || headerFontColor.isEmpty()) headerFontColor = "black";
            if (descriptionFontColor==null || descriptionFontColor.isEmpty()) descriptionFontColor = "black";
            if (priceFontColor==null || priceFontColor.isEmpty()) priceFontColor = "black";


            AdLayout layout = new AdLayout();
            layout.background_color = backgroundColor;
            layout.background_filename="null";
            layout.name="ad2";
            layout.template_id=template_id;
            if (layout.template_id == null ) layout.template_id = "template_1";
            layout.background_filename = "null" ;
            layout.logo_filename = "dd.jpg";
            layout.calltoaction_filename = "buynow.jpg";
            layout.title_font = new FontHolder();
            layout.title_font.name = headerFont==null?"unknown":headerFont;
            layout.title_font.size = headerFontSize;
            layout.title_font.weight = headerFontWeight;
            layout.title_font.color = headerFontColor==null?"unknown":headerFontColor;





            layout.price_font = new FontHolder();
            layout.price_font.name = priceFont==null?"unknown":priceFont;
            layout.price_font.size = priceFontSize;
            layout.price_font.weight = priceFontWeight;
            layout.price_font.color = priceFontColor==null?"black":priceFontColor;

            layout.description_font = new FontHolder();
            layout.description_font.name = descriptionFont==null?"unknown":descriptionFont;
            layout.description_font.size = descriptionFontSize;
            layout.description_font.weight = descriptionFontWeight;
            layout.description_font.color = descriptionFontColor==null?"black":descriptionFontColor;


           // if (template_id.equals( "__embedded")) {
            if (true) {
                Map<String, Object> template_embedded = new HashMap<String, Object>();
                template_embedded.put("name", templateDefName);

                template_embedded.put("title", new TemplateRecord(titleHeight, titleWidth, titleOriginX, titleOriginY));
                template_embedded.put("logo", new TemplateRecord(logoHeight, logoWidth, logoOriginX, logoOriginY));
                template_embedded.put("product", new TemplateRecord(productHeight, productWidth, productOriginX, productOriginY));
                template_embedded.put("description", new TemplateRecord(descriptionHeight, descriptionWidth, descriptionOriginX, descriptionOriginY));
                template_embedded.put("main", new TemplateRecord(mainWidth, mainHeight, 0, 0));
                template_embedded.put("calltoaction", new TemplateRecord(calltoactionHeight, calltoactionWidth, calltoactionOriginX, calltoactionOriginY));
                template_embedded.put("price", new TemplateRecord(priceHeight, priceWidth, priceOriginX, priceOriginY));


                layout.embedded_template = template_embedded;
            }

            String adLayoutJsonString = Jackson.newObjectMapper().writeValueAsString(layout);

            TTransport transport;

            transport = new TSocket("localhost", 9090);
            transport.open();

            ByteBuffer productImage = ByteBuffer.allocate(0);
            ByteBuffer backgroundImage = ByteBuffer.allocate(0);
            ByteBuffer logoImage = ByteBuffer.allocate(0);
            ByteBuffer callToActionImage = ByteBuffer.allocate(0);
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


                fIn = new FileInputStream(callToActionImageFile);
                fChan = fIn.getChannel();
                fSize = fChan.size();
                callToActionImage = ByteBuffer.allocate((int) fSize);
                fChan.read(callToActionImage);
                fChan.close();
                fIn.close();

            } catch (Exception exc) {
                System.out.println("Could Not get files");
                exc.printStackTrace();
                transport.close();
                return null;
            }


            TProtocol protocol = new TBinaryProtocol(transport);
            AdPreviewCreator.Client client = new AdPreviewCreator.Client(protocol);

            productImage.rewind();
            backgroundImage.rewind();
            logoImage.rewind();
            callToActionImage.rewind();
            PreviewInfo previewInfo = new PreviewInfo(productImage, backgroundImage, logoImage, adLayoutJsonString,
                    title, copy, backgroundColor, callToActionImage, price);
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
        public FontHolder price_font;

        public String background_filename;
        public String logo_filename;
        public String calltoaction_filename;
        public String background_color;

        public Map<String,Object> embedded_template;

    }

    private class FontHolder {
        public String name;
        public int size;
        public int weight;
        public String color;
    }

    public static class TemplateRecord {
        public int height;
        public int width;
        public int origin_x;
        public int origin_y;
        public TemplateRecord(int width, int height, int origin_x, int origin_y) {
            this.height = height;
            this.width = width;
            this.origin_x = origin_x;
            this.origin_y = origin_y;

        }
    }
}