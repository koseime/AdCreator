package com.kosei.dropwizard.adcreator.resources;

import com.kosei.dropwizard.adcreator.api.JsonCreativeAsset;
import com.kosei.dropwizard.adcreator.core.PreCannedImagesAndFonts;
import com.kosei.dropwizard.adcreator.core.service.PreviewService;
import com.sun.jersey.multipart.FormDataMultiPart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import resources.PreviewResourceConstants;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by elisaveta.manasieva on 12/10/2014.
 */

@Path(PreviewResource.BASE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class PreviewResource implements PreviewResourceConstants {

  private static final Logger logger = LoggerFactory.getLogger(AdCreatorResource.class);

  private static PreCannedImagesAndFonts preCannedImagesAndFonts;
  private final PreviewService previewService;

  public PreviewResource(PreCannedImagesAndFonts preCannedImagesAndFonts,
                         PreviewService previewService) {
    this.preCannedImagesAndFonts = preCannedImagesAndFonts;
    this.previewService = previewService;
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response createAdCreator(FormDataMultiPart multiPart) throws Exception {

    JsonCreativeAsset
        jsonCreativeAsset = multiPart.getBodyParts().get(0).getEntityAs(JsonCreativeAsset.class);
    InputStream productImageStream = multiPart.getBodyParts().get(1).getEntityAs(InputStream.class);
    InputStream logoImageStream = multiPart.getBodyParts().get(2).getEntityAs(InputStream.class);
    InputStream
        callToActionImageStream =
        multiPart.getBodyParts().get(3).getEntityAs(InputStream.class);

    String uploadedProductImageFile = "";
    String uploadedLogoImageFile = "";
    String uploadedCallToActionImageFile = "";

    if (productImageStream == null || logoImageStream == null || callToActionImageStream == null) {
      uploadedProductImageFile = preCannedImagesAndFonts.getProducts().get(0);
      uploadedLogoImageFile = preCannedImagesAndFonts.getLogos().get(0);
      uploadedCallToActionImageFile = preCannedImagesAndFonts.getCallToActions().get(0);
    } else {
      uploadedProductImageFile = previewService.writeToFile(
          productImageStream,
          multiPart.getBodyParts().get(1)
              .getContentDisposition()
              .getFileName());
      uploadedLogoImageFile = previewService.writeToFile(
          logoImageStream,
          multiPart.getBodyParts().get(2)
              .getContentDisposition()
              .getFileName());
      uploadedCallToActionImageFile =
          previewService.writeToFile(callToActionImageStream,
                                     multiPart.getBodyParts().get(3).getContentDisposition()
                                         .getFileName());
    }

    Map<String, Object> templatesMap = new HashMap<>();
    List<Object> templates = new ArrayList<>();
    templates.add(previewService.getImagesByTemplate(jsonCreativeAsset, uploadedProductImageFile,
                                                     uploadedLogoImageFile,
                                                     uploadedCallToActionImageFile));
    templates.toArray();
    templatesMap.put("templates", templates);

    return Response.ok(templatesMap).build();
  }

}
