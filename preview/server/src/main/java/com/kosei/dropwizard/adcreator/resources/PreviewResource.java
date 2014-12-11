package com.kosei.dropwizard.adcreator.resources;

import com.kosei.dropwizard.adcreator.api.CreativeAsset;
import com.kosei.dropwizard.adcreator.core.PreCannedImagesAndFonts;
import com.kosei.dropwizard.adcreator.core.service.PreviewService;
import com.sun.jersey.multipart.FormDataMultiPart;

import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import resources.PreviewResourceConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
public class PreviewResource implements PreviewResourceConstants{

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

    CreativeAsset creativeAsset = multiPart.getBodyParts().get(0).getEntityAs(CreativeAsset.class);
    InputStream productImageStream = multiPart.getBodyParts().get(1).getEntityAs(InputStream.class);
    InputStream logoImageStream = multiPart.getBodyParts().get(2).getEntityAs(InputStream.class);
    InputStream callToActionImageStream = multiPart.getBodyParts().get(3).getEntityAs(InputStream.class);

    String uploadedproductImageFile = "";
    String uploadedlogoImageFile = "";
    String uploadedcallToActionImageFile = "";

    if (productImageStream == null || logoImageStream == null || callToActionImageStream == null) {
      uploadedproductImageFile = preCannedImagesAndFonts.getProducts().get(0);
      uploadedlogoImageFile = preCannedImagesAndFonts.getLogos().get(0);
      uploadedcallToActionImageFile = preCannedImagesAndFonts.getCallToActions().get(0);
    } else {
      uploadedproductImageFile = writeToFile(productImageStream, multiPart.getBodyParts().get(1).getContentDisposition().getFileName());
      uploadedlogoImageFile = writeToFile(logoImageStream, multiPart.getBodyParts().get(2).getContentDisposition().getFileName());
      uploadedcallToActionImageFile = writeToFile(callToActionImageStream, multiPart.getBodyParts().get(3).getContentDisposition().getFileName());
    }

    Map<String, Object> templatesMap = new HashMap<>();
    List<Object> templates = new ArrayList<>();
    templates.add(previewService.getImagesByTemplate(creativeAsset, uploadedproductImageFile, uploadedlogoImageFile, uploadedcallToActionImageFile));
    templates.toArray();
    templatesMap.put("templates", templates);

    return Response.ok(templatesMap).build();
  }

  private String writeToFile(InputStream inputStream, String fileName) {
    File f = null;
    try {
      f = File.createTempFile(fileName, "", null);
      OutputStream outputStream = new FileOutputStream(f);
      IOUtils.copy(inputStream, outputStream);
      outputStream.close();
    } catch (IOException e) {
      logger.error("IOException " + e);
    }
    System.out.println(f.getAbsolutePath());

    return f.getAbsolutePath();
  }

}
