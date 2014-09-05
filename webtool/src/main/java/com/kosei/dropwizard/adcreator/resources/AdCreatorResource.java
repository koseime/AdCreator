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
    public AdCreatorView createAdCreator(@QueryParam("productImageFile") String product,
                                         @QueryParam("logoImageFile") String logoImage)  throws Exception {
        AdCreator adCreator = new AdCreator();

        if (product==null || product.isEmpty() || logoImage==null || logoImage.isEmpty()) {
            return new AdCreatorView(adCreator, preCannedImagesAndFonts.getFonts().keySet(),
                    preCannedImagesAndFonts.getLogos().keySet(), preCannedImagesAndFonts.getProducts().keySet());
        }

        ByteBuffer bb = client.generate(preCannedImagesAndFonts.getProducts().get(product), preCannedImagesAndFonts.getLogos().get(logoImage));
        adCreator.setId(counter++);

        cache.put(adCreator.getId() + "", bb);
        adCreator.setImageUrl("/adcreator/" + adCreator.getId()+".jpg");
        return new AdCreatorView(adCreator, preCannedImagesAndFonts.getFonts().keySet(),
                preCannedImagesAndFonts.getLogos().keySet(), preCannedImagesAndFonts.getProducts().keySet());
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