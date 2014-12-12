package com.kosei.dropwizard.adcreator.resources;

/**
 * Created by lanceriedel on 9/4/14.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosei.dropwizard.adcreator.core.AdCreator;
import com.kosei.dropwizard.adcreator.core.AdPreviewCreatorClient;
import com.kosei.dropwizard.adcreator.core.CreatedImageCache;
import com.kosei.dropwizard.adcreator.core.PreCannedImagesAndFonts;
import com.kosei.dropwizard.adcreator.views.AdCreatorView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.jackson.Jackson;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/adcreator")
@Produces(MediaType.APPLICATION_JSON)
public class AdCreatorResource {
    private static final Logger logger = LoggerFactory.getLogger(AdCreatorResource.class);

    private final CreatedImageCache cache;
    private final AdPreviewCreatorClient client;
    private static int counter = 0;
    private static PreCannedImagesAndFonts preCannedImagesAndFonts;

    public AdCreatorResource(CreatedImageCache cache, AdPreviewCreatorClient client, PreCannedImagesAndFonts preCannedImagesAndFonts) {
        this.cache = cache;
        this.client = client;
        this.preCannedImagesAndFonts = preCannedImagesAndFonts;
    }
      /*

          public int productHeight, productWidth, productOriginX, productOriginY;
    public int titleHeight, titleWidth, titleOriginX, titleOriginY;
    public int descriptionHeight, descriptionWidth, descriptionOriginX, descriptionOriginY;
    public int priceHeight, priceWidth, priceOriginX, priceOriginY;
    public int logoHeight, logoWidth, logoOriginX, logoOriginY;

    public int calltoactionHeight, calltoactionWidth, calltoactionOriginX, calltoactionOriginY;
    public int mainHeight, mainWidth, mainOriginX, mainOriginY;
    public String templateDefName;


       */

    @GET
    @Path("/create")
    @Produces(MediaType.TEXT_HTML)
    public AdCreatorView createAdCreator(@QueryParam("productImage") String product,
                                         @QueryParam("logoImage") String logoImage,
                                         @QueryParam("callToActionImage") String callToActionImage,
                                         @QueryParam("headerFont") String headerFont,
                                         @QueryParam("headerFontSize") String headerFontSize,
                                         @QueryParam("headerFontWeight") String headerFontWeight,
                                         @QueryParam("headerFontColor") String headerFontColor,
                                         @QueryParam("priceFont") String priceFont,
                                         @QueryParam("priceFontSize") String priceFontSize,
                                         @QueryParam("priceFontWeight") String priceFontWeight,
                                         @QueryParam("priceFontColor") String priceFontColor,
                                         @QueryParam("descriptionFont") String descriptionFont,
                                         @QueryParam("descriptionFontSize") String descriptionFontSize,
                                         @QueryParam("descriptionFontWeight") String descriptionFontWeight,
                                         @QueryParam("descriptionFontColor") String descriptionFontColor,
                                         @QueryParam("descriptionText") String descriptionText,
                                         @QueryParam("headerText") String headerText,
                                         @QueryParam("priceText") String priceText,

                                         @QueryParam("backgroundColor") String backgroundColor,
                                         @QueryParam("templateId") String templateId,
                                         @QueryParam("productHeight") int productHeight,
                                         @QueryParam("productWidth") int productWidth,
                                         @QueryParam("productOriginX") int productOriginX,
                                         @QueryParam("productOriginY") int productOriginY,
                                         @QueryParam("titleHeight") int titleHeight,
                                         @QueryParam("titleWidth") int titleWidth,
                                         @QueryParam("titleOriginX") int titleOriginX,
                                         @QueryParam("titleOriginY") int titleOriginY,
                                         @QueryParam("descriptionHeight") int descriptionHeight,
                                         @QueryParam("descriptionWidth") int descriptionWidth,
                                         @QueryParam("descriptionOriginX") int descriptionOriginX,
                                         @QueryParam("descriptionOriginY") int descriptionOriginY,
                                         @QueryParam("priceHeight") int priceHeight,
                                         @QueryParam("priceWidth") int priceWidth,
                                         @QueryParam("priceOriginX") int priceOriginX,
                                         @QueryParam("priceOriginY") int priceOriginY,
                                         @QueryParam("logoHeight") int logoHeight,
                                         @QueryParam("logoWidth") int logoWidth,
                                         @QueryParam("logoOriginX") int logoOriginX,
                                         @QueryParam("logoOriginY") int logoOriginY,
                                         @QueryParam("calltoactionHeight") int calltoactionHeight,
                                         @QueryParam("calltoactionWidth") int calltoactionWidth,
                                         @QueryParam("calltoactionOriginX") int calltoactionOriginX,
                                         @QueryParam("calltoactionOriginY") int calltoactionOriginY,
                                         @QueryParam("mainHeight") int mainHeight,
                                         @QueryParam("mainWidth") int mainWidth,
                                         @QueryParam("templateDefName") String templateDefName

    ) throws Exception
  {
    AdCreator adCreator = new AdCreator();

    if (product == null || product.isEmpty() || logoImage == null || logoImage.isEmpty() || descriptionFontWeight == null || descriptionFont == null
        || descriptionFontSize == null || headerFont == null || headerFontSize == null || headerFontWeight == null) {
      logger.info("Missing params");
      AdCreatorView view = new AdCreatorView(adCreator, preCannedImagesAndFonts.getFonts().keySet(),
                                             preCannedImagesAndFonts.getLogos().keySet(), preCannedImagesAndFonts.getCallToActions().keySet(),
                                             preCannedImagesAndFonts.getProducts().keySet(),
                                             preCannedImagesAndFonts.getSizes(), preCannedImagesAndFonts.getWeights(), preCannedImagesAndFonts.templateIds(), preCannedImagesAndFonts.getTemplates().keySet());

      view.selectedTemplateId = templateId;
      view.selectedProduct = product;


      view.selectedLogo = logoImage;
      view.selectedCallToAction = callToActionImage;


      view.selectedDescriptionFont = descriptionFont;
      view.selectedDescriptionFontSize = descriptionFontSize;
      view.selectedDescriptionFontWeight = descriptionFontWeight;
      view.descriptionFontColor = descriptionFontColor;
      view.descriptionText = descriptionText;

      view.selectedHeaderFont = headerFont;
      view.selectedHeaderFontSize = headerFontSize;
      view.selectedHeaderFontWeight = headerFontWeight;
      view.headerFontColor = headerFontColor;
      view.headerText = headerText;

      view.selectedPriceFont = priceFont;
      view.selectedPriceFontSize = priceFontSize;
      view.selectedPriceFontWeight = priceFontWeight;
      view.priceFontColor = priceFontColor;
      view.priceText = priceText;


      view.backgroundColor = backgroundColor;

      view.titleHeight = titleHeight;
      view.titleWidth = titleWidth;
      view.titleOriginX = titleOriginX;
      view.titleOriginY = titleOriginY;

      view.calltoactionHeight = calltoactionHeight;
      view.calltoactionWidth = calltoactionWidth;
      view.calltoactionOriginX = calltoactionOriginX;
      view.calltoactionOriginY = calltoactionOriginY;

      view.descriptionHeight = descriptionHeight;
      view.descriptionWidth = descriptionWidth;
      view.descriptionOriginX = descriptionOriginX;
      view.descriptionOriginY = descriptionOriginY;

      view.productHeight = productHeight;
      view.productWidth = productWidth;
      view.productOriginX = productOriginX;
      view.productOriginY = productOriginY;


      view.priceHeight = priceHeight;
      view.priceWidth = priceWidth;
      view.priceOriginX = priceOriginX;
      view.priceOriginY = priceOriginY;

      view.logoHeight = logoHeight;
      view.logoWidth = logoWidth;
      view.logoOriginX = logoOriginX;
      view.logoOriginY = logoOriginY;

      view.mainHeight = mainHeight;
      view.mainWidth = mainWidth;

      view.mainOriginX = 0;
      view.mainOriginY = 0;

      view.templateDefName = templateDefName;


      return view;
    }
    if (priceFont == null || priceFontSize == null || priceFontWeight == null || priceFont.contains("HTMLSelectElement")) {
      priceFont = "Sans";
      priceFontSize = "10";
      priceFontWeight = "500";
      priceFontColor = "black";

    }
    if (backgroundColor == null) backgroundColor = "white";
    String sBackgroundColor = backgroundColor;
    if (descriptionFontColor == null) descriptionFontColor = "black";
    String sDescriptionFontColor = descriptionFontColor;
    if (headerFontColor == null) headerFontColor = "black";
    String sHeaderFontColor = headerFontColor;
    if (priceFontColor == null) priceFontColor = "black";
    String sPriceFontColor = priceFontColor;

    if (backgroundColor.length() == 6) sBackgroundColor = "#" + sBackgroundColor;
    if (descriptionFontColor.length() == 6) sDescriptionFontColor = "#" + sDescriptionFontColor;
    if (headerFontColor.length() == 6) sHeaderFontColor = "#" + sHeaderFontColor;
    if (priceFontColor.length() == 6) sPriceFontColor = "#" + sPriceFontColor;

    long start = System.currentTimeMillis();
    ByteBuffer bb = client.generate(
        headerText,
        descriptionText,
        priceText,
        preCannedImagesAndFonts.getProducts().get(product),
        preCannedImagesAndFonts.getLogos().get(logoImage),
        preCannedImagesAndFonts.getCallToActions().get(callToActionImage),
        preCannedImagesAndFonts.getFonts().get(headerFont),
        Integer.parseInt(headerFontSize),
        Integer.parseInt(headerFontWeight),
        sHeaderFontColor,
        preCannedImagesAndFonts.getFonts().get(priceFont),
        Integer.parseInt(priceFontSize),
        Integer.parseInt(priceFontWeight),
        sPriceFontColor,
        preCannedImagesAndFonts.getFonts().get(descriptionFont),
        Integer.parseInt(descriptionFontSize),
        Integer.parseInt(descriptionFontWeight),
        sDescriptionFontColor,
        sBackgroundColor,
        templateId,

        productHeight,
        productWidth,
        productOriginX,
        productOriginY,
        titleHeight,
        titleWidth,
        titleOriginX,
        titleOriginY,
        descriptionHeight,
        descriptionWidth,
        descriptionOriginX,
        descriptionOriginY,
        priceHeight,
        priceWidth,
        priceOriginX,
        priceOriginY,
        logoHeight,
        logoWidth,
        logoOriginX,
        logoOriginY,
        calltoactionHeight,
        calltoactionWidth,
        calltoactionOriginX,
        calltoactionOriginY,
        mainHeight,
        mainWidth,
        templateDefName

    );
    adCreator.setId(counter++);
    System.out.println("Time to generate:" + (System.currentTimeMillis() - start));

    cache.put(adCreator.getId() + "", bb);
    adCreator.setImageUrl("/adcreator/" + adCreator.getId() + ".jpg");
    AdCreatorView view = new AdCreatorView(adCreator,
                                           preCannedImagesAndFonts.getFonts().keySet(),
                                           preCannedImagesAndFonts.getLogos().keySet(),
                                           preCannedImagesAndFonts.getCallToActions().keySet(),
                                           preCannedImagesAndFonts.getProducts().keySet(),
                                           preCannedImagesAndFonts.getSizes(),
                                           preCannedImagesAndFonts.getWeights(),
                                           preCannedImagesAndFonts.templateIds(),
                                           preCannedImagesAndFonts.getTemplates().keySet());


    view.selectedTemplateId = templateId;
    view.selectedProduct = product;


    view.selectedLogo = logoImage;
    view.selectedCallToAction = callToActionImage;


    view.selectedDescriptionFont = descriptionFont;
    view.selectedDescriptionFontSize = descriptionFontSize;
    view.selectedDescriptionFontWeight = descriptionFontWeight;
    view.descriptionFontColor = descriptionFontColor;
    view.descriptionText = descriptionText;

    view.selectedHeaderFont = headerFont;
    view.selectedHeaderFontSize = headerFontSize;
    view.selectedHeaderFontWeight = headerFontWeight;
    view.headerFontColor = headerFontColor;
    view.headerText = headerText;

    view.selectedPriceFont = priceFont;
    view.selectedPriceFontSize = priceFontSize;
    view.selectedPriceFontWeight = priceFontWeight;
    view.priceFontColor = priceFontColor;
    view.priceText = priceText;


    view.backgroundColor = backgroundColor;

    view.titleHeight = titleHeight;
    view.titleWidth = titleWidth;
    view.titleOriginX = titleOriginX;
    view.titleOriginY = titleOriginY;

    view.calltoactionHeight = calltoactionHeight;
    view.calltoactionWidth = calltoactionWidth;
    view.calltoactionOriginX = calltoactionOriginX;
    view.calltoactionOriginY = calltoactionOriginY;

    view.descriptionHeight = descriptionHeight;
    view.descriptionWidth = descriptionWidth;
    view.descriptionOriginX = descriptionOriginX;
    view.descriptionOriginY = descriptionOriginY;

    view.productHeight = productHeight;
    view.productWidth = productWidth;
    view.productOriginX = productOriginX;
    view.productOriginY = productOriginY;


    view.priceHeight = priceHeight;
    view.priceWidth = priceWidth;
    view.priceOriginX = priceOriginX;
    view.priceOriginY = priceOriginY;

    view.logoHeight = logoHeight;
    view.logoWidth = logoWidth;
    view.logoOriginX = logoOriginX;
    view.logoOriginY = logoOriginY;

    view.mainHeight = mainHeight;
    view.mainWidth = mainWidth;

    view.mainOriginX = 0;
    view.mainOriginY = 0;

    view.templateDefName = templateDefName;

    return view;
  }

    @Path("{externalId}.jpg")
    @GET
    @Produces({"image/jpg"})
    public Response getAsImage(@PathParam("externalId") String externalId) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // do something with externalId, maybe retrieve an object from the
        // db, then calculate data, size, expirationTimestamp, etc

        try {
            //get bytes for image
            ByteBuffer buff = cache.get(externalId);
            DataOutputStream w = new DataOutputStream(stream);

            w.write(buff.array());

            w.flush();
            w.close();

        } catch (Exception e) {
            // ExceptionMapper will return HTTP 500
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }

        CacheControl cc = new CacheControl();
        cc.setNoTransform(true);
        cc.setMustRevalidate(false);
        cc.setNoCache(false);
        cc.setMaxAge(1);


        Response response = Response
                .ok()
                .cacheControl(cc)
                .type("image/jpg")
                .entity(stream.toByteArray())
                .build();
        return response;
    }


    @GET
    @Path("/savetemplate")
    @Produces(MediaType.TEXT_HTML)
    public AdCreatorView saveTemplate(@QueryParam("productImage") String product,
                                      @QueryParam("logoImage") String logoImage,
                                      @QueryParam("callToActionImage") String callToActionImage,
                                      @QueryParam("headerFont") String headerFont,
                                      @QueryParam("headerFontSize") String headerFontSize,
                                      @QueryParam("headerFontWeight") String headerFontWeight,
                                      @QueryParam("headerFontColor") String headerFontColor,
                                      @QueryParam("priceFont") String priceFont,
                                      @QueryParam("priceFontSize") String priceFontSize,
                                      @QueryParam("priceFontWeight") String priceFontWeight,
                                      @QueryParam("priceFontColor") String priceFontColor,
                                      @QueryParam("descriptionFont") String descriptionFont,
                                      @QueryParam("descriptionFontSize") String descriptionFontSize,
                                      @QueryParam("descriptionFontWeight") String descriptionFontWeight,
                                      @QueryParam("descriptionFontColor") String descriptionFontColor,
                                      @QueryParam("descriptionText") String descriptionText,
                                      @QueryParam("headerText") String headerText,
                                      @QueryParam("priceText") String priceText,

                                      @QueryParam("backgroundColor") String backgroundColor,
                                      @QueryParam("templateId") String templateId,
                                      @QueryParam("productHeight") int productHeight,
                                      @QueryParam("productWidth") int productWidth,
                                      @QueryParam("productOriginX") int productOriginX,
                                      @QueryParam("productOriginY") int productOriginY,
                                      @QueryParam("titleHeight") int titleHeight,
                                      @QueryParam("titleWidth") int titleWidth,
                                      @QueryParam("titleOriginX") int titleOriginX,
                                      @QueryParam("titleOriginY") int titleOriginY,
                                      @QueryParam("descriptionHeight") int descriptionHeight,
                                      @QueryParam("descriptionWidth") int descriptionWidth,
                                      @QueryParam("descriptionOriginX") int descriptionOriginX,
                                      @QueryParam("descriptionOriginY") int descriptionOriginY,
                                      @QueryParam("priceHeight") int priceHeight,
                                      @QueryParam("priceWidth") int priceWidth,
                                      @QueryParam("priceOriginX") int priceOriginX,
                                      @QueryParam("priceOriginY") int priceOriginY,
                                      @QueryParam("logoHeight") int logoHeight,
                                      @QueryParam("logoWidth") int logoWidth,
                                      @QueryParam("logoOriginX") int logoOriginX,
                                      @QueryParam("logoOriginY") int logoOriginY,
                                      @QueryParam("calltoactionHeight") int calltoactionHeight,
                                      @QueryParam("calltoactionWidth") int calltoactionWidth,
                                      @QueryParam("calltoactionOriginX") int calltoactionOriginX,
                                      @QueryParam("calltoactionOriginY") int calltoactionOriginY,
                                      @QueryParam("mainHeight") int mainHeight,
                                      @QueryParam("mainWidth") int mainWidth,
                                      @QueryParam("templateDefName") String templateDefName

    ) throws Exception {
        AdCreator adCreator = new AdCreator();


        logger.info("Save template");
        AdCreatorView view = new AdCreatorView(adCreator, preCannedImagesAndFonts.getFonts().keySet(),
                preCannedImagesAndFonts.getLogos().keySet(), preCannedImagesAndFonts.getCallToActions().keySet(),
                preCannedImagesAndFonts.getProducts().keySet(),
                preCannedImagesAndFonts.getSizes(), preCannedImagesAndFonts.getWeights(), preCannedImagesAndFonts.templateIds(),
                preCannedImagesAndFonts.getTemplates().keySet());

        view.selectedTemplateId = templateId;
        view.selectedProduct = product;


        view.selectedLogo = logoImage;
        view.selectedCallToAction = callToActionImage;


        view.selectedDescriptionFont = descriptionFont;
        view.selectedDescriptionFontSize = descriptionFontSize;
        view.selectedDescriptionFontWeight = descriptionFontWeight;
        view.descriptionFontColor = descriptionFontColor;
        view.descriptionText = descriptionText;

        view.selectedHeaderFont = headerFont;
        view.selectedHeaderFontSize = headerFontSize;
        view.selectedHeaderFontWeight = headerFontWeight;
        view.headerFontColor = headerFontColor;
        view.headerText = headerText;

        view.selectedPriceFont = priceFont;
        view.selectedPriceFontSize = priceFontSize;
        view.selectedPriceFontWeight = priceFontWeight;
        view.priceFontColor = priceFontColor;
        view.priceText = priceText;


        view.backgroundColor = backgroundColor;

        view.titleHeight = titleHeight;
        view.titleWidth = titleWidth;
        view.titleOriginX = titleOriginX;
        view.titleOriginY = titleOriginY;

        view.calltoactionHeight = calltoactionHeight;
        view.calltoactionWidth = calltoactionWidth;
        view.calltoactionOriginX = calltoactionOriginX;
        view.calltoactionOriginY = calltoactionOriginY;

        view.descriptionHeight = descriptionHeight;
        view.descriptionWidth = descriptionWidth;
        view.descriptionOriginX = descriptionOriginX;
        view.descriptionOriginY = descriptionOriginY;

        view.productHeight = productHeight;
        view.productWidth = productWidth;
        view.productOriginX = productOriginX;
        view.productOriginY = productOriginY;


        view.priceHeight = priceHeight;
        view.priceWidth = priceWidth;
        view.priceOriginX = priceOriginX;
        view.priceOriginY = priceOriginY;

        view.logoHeight = logoHeight;
        view.logoWidth = logoWidth;
        view.logoOriginX = logoOriginX;
        view.logoOriginY = logoOriginY;

        view.mainHeight = mainHeight;
        view.mainWidth = mainWidth;

        view.mainOriginX = 0;
        view.mainOriginY = 0;

        view.templateDefName = templateDefName;


        Map<String, Object> template_embedded = new HashMap<String, Object>();
        template_embedded.put("name", templateDefName);

        template_embedded.put("title", new AdPreviewCreatorClient.TemplateRecord(titleHeight, titleWidth, titleOriginX, titleOriginY));
        template_embedded.put("logo", new AdPreviewCreatorClient.TemplateRecord(logoHeight, logoWidth, logoOriginX, logoOriginY));
        template_embedded.put("product", new AdPreviewCreatorClient.TemplateRecord(productHeight, productWidth, productOriginX, productOriginY));
        template_embedded.put("description", new AdPreviewCreatorClient.TemplateRecord(descriptionHeight, descriptionWidth, descriptionOriginX, descriptionOriginY));
        template_embedded.put("main", new AdPreviewCreatorClient.TemplateRecord(mainWidth, mainHeight, 0, 0));
        template_embedded.put("calltoaction", new AdPreviewCreatorClient.TemplateRecord(calltoactionHeight, calltoactionWidth, calltoactionOriginX, calltoactionOriginY));
        template_embedded.put("price", new AdPreviewCreatorClient.TemplateRecord(priceHeight, priceWidth, priceOriginX, priceOriginY));
        String adLayoutJsonString = Jackson.newObjectMapper().writeValueAsString(template_embedded);


        String outFile = preCannedImagesAndFonts.getTemplateDir() + "/" + templateDefName;
        if (!outFile.endsWith(".json")) outFile = outFile + ".json";


        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFile), "utf-8"));
            writer.write(adLayoutJsonString);
        } catch (IOException ex) {
            // report
        } finally {
            try {writer.close();} catch (Exception ex) {}
        }
        return view;
    }



    @GET
    @Path("/loadtemplate")
    @Produces(MediaType.TEXT_HTML)
    public AdCreatorView loadTemplate(@QueryParam("productImage") String product,
                                      @QueryParam("logoImage") String logoImage,
                                      @QueryParam("callToActionImage") String callToActionImage,
                                      @QueryParam("headerFont") String headerFont,
                                      @QueryParam("headerFontSize") String headerFontSize,
                                      @QueryParam("headerFontWeight") String headerFontWeight,
                                      @QueryParam("headerFontColor") String headerFontColor,
                                      @QueryParam("priceFont") String priceFont,
                                      @QueryParam("priceFontSize") String priceFontSize,
                                      @QueryParam("priceFontWeight") String priceFontWeight,
                                      @QueryParam("priceFontColor") String priceFontColor,
                                      @QueryParam("descriptionFont") String descriptionFont,
                                      @QueryParam("descriptionFontSize") String descriptionFontSize,
                                      @QueryParam("descriptionFontWeight") String descriptionFontWeight,
                                      @QueryParam("descriptionFontColor") String descriptionFontColor,
                                      @QueryParam("descriptionText") String descriptionText,
                                      @QueryParam("headerText") String headerText,
                                      @QueryParam("priceText") String priceText,

                                      @QueryParam("backgroundColor") String backgroundColor,
                                      @QueryParam("templateId") String templateId,
                                      @QueryParam("productHeight") int productHeight,
                                      @QueryParam("productWidth") int productWidth,
                                      @QueryParam("productOriginX") int productOriginX,
                                      @QueryParam("productOriginY") int productOriginY,
                                      @QueryParam("titleHeight") int titleHeight,
                                      @QueryParam("titleWidth") int titleWidth,
                                      @QueryParam("titleOriginX") int titleOriginX,
                                      @QueryParam("titleOriginY") int titleOriginY,
                                      @QueryParam("descriptionHeight") int descriptionHeight,
                                      @QueryParam("descriptionWidth") int descriptionWidth,
                                      @QueryParam("descriptionOriginX") int descriptionOriginX,
                                      @QueryParam("descriptionOriginY") int descriptionOriginY,
                                      @QueryParam("priceHeight") int priceHeight,
                                      @QueryParam("priceWidth") int priceWidth,
                                      @QueryParam("priceOriginX") int priceOriginX,
                                      @QueryParam("priceOriginY") int priceOriginY,
                                      @QueryParam("logoHeight") int logoHeight,
                                      @QueryParam("logoWidth") int logoWidth,
                                      @QueryParam("logoOriginX") int logoOriginX,
                                      @QueryParam("logoOriginY") int logoOriginY,
                                      @QueryParam("calltoactionHeight") int calltoactionHeight,
                                      @QueryParam("calltoactionWidth") int calltoactionWidth,
                                      @QueryParam("calltoactionOriginX") int calltoactionOriginX,
                                      @QueryParam("calltoactionOriginY") int calltoactionOriginY,
                                      @QueryParam("mainHeight") int mainHeight,
                                      @QueryParam("mainWidth") int mainWidth,
                                      @QueryParam("templateDefName") String templateDefName

    ) throws Exception {
        AdCreator adCreator = new AdCreator();


        logger.info("Save template");
        AdCreatorView view = new AdCreatorView(adCreator, preCannedImagesAndFonts.getFonts().keySet(),
                preCannedImagesAndFonts.getLogos().keySet(), preCannedImagesAndFonts.getCallToActions().keySet(),
                preCannedImagesAndFonts.getProducts().keySet(),
                preCannedImagesAndFonts.getSizes(), preCannedImagesAndFonts.getWeights(), preCannedImagesAndFonts.templateIds(),
                preCannedImagesAndFonts.getTemplates().keySet());

        if (templateDefName.endsWith(".json"))
            templateDefName = templateDefName.substring(0,templateDefName.length()-5);

        String inFile = preCannedImagesAndFonts.getTemplateDir() + "/" + templateDefName;
        if (!inFile.endsWith(".json")) inFile = inFile + ".json";



        ObjectMapper mapper = Jackson.newObjectMapper();
        Map<String, Object> embedded = null;
        try {
            embedded = mapper.readValue(new File(inFile), Map.class);

        } catch (Exception e) {
            e.printStackTrace();
        }


        view.selectedTemplateId = templateId;
        view.selectedProduct = product;


        view.selectedLogo = logoImage;
        view.selectedCallToAction = callToActionImage;


        view.selectedDescriptionFont = descriptionFont;
        view.selectedDescriptionFontSize = descriptionFontSize;
        view.selectedDescriptionFontWeight = descriptionFontWeight;
        view.descriptionFontColor = descriptionFontColor;
        view.descriptionText = descriptionText;

        view.selectedHeaderFont = headerFont;
        view.selectedHeaderFontSize = headerFontSize;
        view.selectedHeaderFontWeight = headerFontWeight;
        view.headerFontColor = headerFontColor;
        view.headerText = headerText;

        view.selectedPriceFont = priceFont;
        view.selectedPriceFontSize = priceFontSize;
        view.selectedPriceFontWeight = priceFontWeight;
        view.priceFontColor = priceFontColor;
        view.priceText = priceText;


        view.backgroundColor = backgroundColor;

        view.titleHeight = titleHeight;
        view.titleWidth = titleWidth;
        view.titleOriginX = titleOriginX;
        view.titleOriginY = titleOriginY;

        view.calltoactionHeight = calltoactionHeight;
        view.calltoactionWidth = calltoactionWidth;
        view.calltoactionOriginX = calltoactionOriginX;
        view.calltoactionOriginY = calltoactionOriginY;

        view.descriptionHeight = descriptionHeight;
        view.descriptionWidth = descriptionWidth;
        view.descriptionOriginX = descriptionOriginX;
        view.descriptionOriginY = descriptionOriginY;

        view.productHeight = productHeight;
        view.productWidth = productWidth;
        view.productOriginX = productOriginX;
        view.productOriginY = productOriginY;


        view.priceHeight = priceHeight;
        view.priceWidth = priceWidth;
        view.priceOriginX = priceOriginX;
        view.priceOriginY = priceOriginY;

        view.logoHeight = logoHeight;
        view.logoWidth = logoWidth;
        view.logoOriginX = logoOriginX;
        view.logoOriginY = logoOriginY;

        view.mainHeight = mainHeight;
        view.mainWidth = mainWidth;

        view.mainOriginX = 0;
        view.mainOriginY = 0;

        view.templateDefName = templateDefName;

        if (embedded!=null) {
            Map titleRec = (Map) embedded.get("title");
            view.titleHeight = (Integer)titleRec.get("height");
            view.titleWidth = (Integer)titleRec.get("width");
            view.titleOriginX = (Integer)titleRec.get("origin_x");
            view.titleOriginY = (Integer)titleRec.get("origin_y");

            Map calltoactionRec = (Map) embedded.get("calltoaction");
            view.calltoactionHeight = (Integer)calltoactionRec.get("height");
            view.calltoactionWidth = (Integer)calltoactionRec.get("width");
            view.calltoactionOriginX = (Integer)calltoactionRec.get("origin_x");
            view.calltoactionOriginY = (Integer)calltoactionRec.get("origin_y");

            Map descriptionRec = (Map) embedded.get("description");
            view.descriptionHeight = (Integer)descriptionRec.get("height");
            view.descriptionWidth = (Integer)descriptionRec.get("width");
            view.descriptionOriginX = (Integer)descriptionRec.get("origin_x");
            view.descriptionOriginY = (Integer)descriptionRec.get("origin_y");

            Map productRec = (Map) embedded.get("product");
            view.productHeight = (Integer)productRec.get("height");
            view.productWidth = (Integer)productRec.get("width");
            view.productOriginX = (Integer)productRec.get("origin_x");
            view.productOriginY = (Integer)productRec.get("origin_y");


            Map priceRec = (Map) embedded.get("price");
            view.priceHeight = (Integer)priceRec.get("height");
            view.priceWidth = (Integer)priceRec.get("width");
            view.priceOriginX = (Integer)priceRec.get("origin_x");
            view.priceOriginY = (Integer)priceRec.get("origin_y");

            Map logoRec = (Map) embedded.get("logo");
            view.logoHeight = (Integer)logoRec.get("height");
            view.logoWidth = (Integer)logoRec.get("width");
            view.logoOriginX = (Integer)logoRec.get("origin_x");
            view.logoOriginY = (Integer)logoRec.get("origin_y");

            Map mainRec = (Map) embedded.get("main");
            view.mainHeight = (Integer)mainRec.get("height");
            view.mainWidth = (Integer)mainRec.get("width");

        }

        return view;
    }

}