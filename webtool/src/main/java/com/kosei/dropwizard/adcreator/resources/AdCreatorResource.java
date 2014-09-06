package com.kosei.dropwizard.adcreator.resources;

/**
 * Created by lanceriedel on 9/4/14.
 */
import com.kosei.dropwizard.adcreator.core.*;
import com.kosei.dropwizard.adcreator.views.AdCreatorView;
import com.kosei.dropwizard.adcreator.views.PersonView;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.ByteBuffer;

@Path("/adcreator")
@Produces(MediaType.APPLICATION_JSON)
public class AdCreatorResource {
    private final CreatedImageCache cache;
    private final AdPreviewCreatorClient client;
    private static int counter = 0;
    private static PreCannedImagesAndFonts preCannedImagesAndFonts;

    public AdCreatorResource(CreatedImageCache cache, AdPreviewCreatorClient client, PreCannedImagesAndFonts preCannedImagesAndFonts) {
        this.cache = cache; this.client = client;
        this.preCannedImagesAndFonts = preCannedImagesAndFonts;
    }


    @GET
    @Path("/create")
    @Produces(MediaType.TEXT_HTML)
    public AdCreatorView createAdCreator(@QueryParam("productImage") String product,
                                         @QueryParam("logoImage") String logoImage,
                                         @QueryParam("headerFont") String headerFont,
                                         @QueryParam("headerFontSize") String headerFontSize,
                                         @QueryParam("headerFontWeight") String headerFontWeight,
                                         @QueryParam("headerFontColor") String headerFontColor,
                                         @QueryParam("descriptionFont") String descriptionFont,
                                         @QueryParam("descriptionFontSize") String descriptionFontSize,
                                         @QueryParam("descriptionFontWeight") String descriptionFontWeight,
                                         @QueryParam("descriptionFontColor") String descriptionFontColor,
                                         @QueryParam("descriptionText") String descriptionText,
                                         @QueryParam("headerText") String headerText ,
                                         @QueryParam("backgroundColor") String backgroundColor,
                                         @QueryParam("templateId") String templateId



    )  throws Exception {
        AdCreator adCreator = new AdCreator();

        if (product==null || product.isEmpty() || logoImage==null || logoImage.isEmpty() || descriptionFontWeight ==null || descriptionFont == null
                || descriptionFontSize == null || headerFont ==null || headerFontSize ==null || headerFontWeight == null) {
            return new AdCreatorView(adCreator, preCannedImagesAndFonts.getFonts().keySet(),
                    preCannedImagesAndFonts.getLogos().keySet(), preCannedImagesAndFonts.getProducts().keySet(),
                    preCannedImagesAndFonts.getSizes(), preCannedImagesAndFonts.getWeights(), preCannedImagesAndFonts.templateIds());
        }
        if (backgroundColor==null) backgroundColor = "white";
        String sBackgroundColor = backgroundColor;
        if (descriptionFontColor==null) descriptionFontColor = "black";
        String sDescriptionFontColor = descriptionFontColor;
        if (headerFontColor==null) headerFontColor = "black";
        String sHeaderFontColor = headerFontColor;

        if (backgroundColor.length()==6) sBackgroundColor = "#"+sBackgroundColor;
        if (descriptionFontColor.length()==6) sDescriptionFontColor = "#"+sDescriptionFontColor;
        if (backgroundColor.length()==6) sHeaderFontColor = "#"+sHeaderFontColor;


        long start = System.currentTimeMillis();
        ByteBuffer bb = client.generate(
                headerText,
                descriptionText,
                preCannedImagesAndFonts.getProducts().get(product),
                preCannedImagesAndFonts.getLogos().get(logoImage),
                preCannedImagesAndFonts.getFonts().get(headerFont),
                Integer.parseInt(headerFontSize),
                Integer.parseInt(headerFontWeight),
                sHeaderFontColor,
                preCannedImagesAndFonts.getFonts().get(descriptionFont),
                Integer.parseInt(descriptionFontSize),
                Integer.parseInt(descriptionFontWeight),
                sDescriptionFontColor,
                sBackgroundColor,
                templateId);
        adCreator.setId(counter++);
        System.out.println("Time to generate:" + (System.currentTimeMillis()-start));

        cache.put(adCreator.getId() + "", bb);
        adCreator.setImageUrl("/adcreator/" + adCreator.getId()+".jpg");
        AdCreatorView view = new AdCreatorView(adCreator,
                preCannedImagesAndFonts.getFonts().keySet(),
                preCannedImagesAndFonts.getLogos().keySet(),
                preCannedImagesAndFonts.getProducts().keySet(),
                preCannedImagesAndFonts.getSizes(),
                preCannedImagesAndFonts.getWeights(),
                preCannedImagesAndFonts.templateIds());


        view.selectedDescriptionFont  = descriptionFont;
        view.selectedDescriptionFontSize =descriptionFontSize;
        view.selectedDescriptionFontWeight =descriptionFontWeight;
        view.descriptionFontColor = descriptionFontColor;

        view.selectedHeaderFont=headerFont;
        view.selectedHeaderFontSize=headerFontSize;
        view.selectedHeaderFontWeight=headerFontWeight;
        view.selectedTemplateId = templateId;
        view.headerFontColor = headerFontColor;

        view.selectedLogo = logoImage;
        view.selectedProduct = product;
        view.descriptionText = descriptionText;
        view.headerText = headerText;
        view.backgroundColor = backgroundColor;

        return view;
    }


    @Path("{externalId}.jpg")
    @GET
    @Produces({"image/jpg"})
    public Response getAsImage(@PathParam("externalId") String externalId)  {

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


}