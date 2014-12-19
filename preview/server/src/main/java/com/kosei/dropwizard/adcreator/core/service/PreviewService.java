package com.kosei.dropwizard.adcreator.core.service;

import com.kosei.dropwizard.adcreator.api.JsonCreativeAsset;
import com.kosei.dropwizard.adcreator.api.JsonTemplate;
import com.kosei.dropwizard.adcreator.core.AdCreator;
import com.kosei.dropwizard.adcreator.core.AdPreviewCreatorClient;
import com.kosei.dropwizard.adcreator.core.CreatedImageCache;

import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by elisaveta.manasieva on 12/11/2014.
 */
public class PreviewService {

  private static final Logger
      logger = LoggerFactory.getLogger(PreviewService.class);

  private final AdPreviewCreatorClient client;
  private final CreatedImageCache cache;
  private static int counter = 0;

  public PreviewService(AdPreviewCreatorClient client, CreatedImageCache cache) {
    this.client = client;
    this.cache = cache;
  }

  private ByteBuffer generatePreview(JsonCreativeAsset jsonCreativeAsset, JsonTemplate jsonTemplate,
                                     String uploadedproductImageFile, String uploadedlogoImageFile,
                                     String uploadedcallToActionImageFile)
      throws Exception {
    ByteBuffer bb = client.generate(
        jsonCreativeAsset.title,
        jsonCreativeAsset.body,
        jsonCreativeAsset.price,
        uploadedproductImageFile,
        uploadedlogoImageFile,
        uploadedcallToActionImageFile,
        jsonCreativeAsset.titleFont,
        jsonCreativeAsset.titleFontSize,
        jsonCreativeAsset.titleFontWeight,
        jsonCreativeAsset.titleFontColor,
        jsonCreativeAsset.priceFont,
        jsonCreativeAsset.priceFontSize,
        Integer.parseInt(jsonCreativeAsset.priceFontWeight),
        jsonCreativeAsset.priceFontColor,
        jsonCreativeAsset.bodyFont,
        jsonCreativeAsset.bodyFontSize,
        Integer.parseInt(jsonCreativeAsset.bodyFontWeight),
        jsonCreativeAsset.bodyFontColor,
        jsonCreativeAsset.backgroundColor,
        "",
        jsonTemplate.product.height,
        jsonTemplate.product.width,
        jsonTemplate.product.origin_x,
        jsonTemplate.product.origin_y,
        jsonTemplate.title.height,
        jsonTemplate.title.width,
        jsonTemplate.title.origin_x,
        jsonTemplate.title.origin_y,
        jsonTemplate.description.height,
        jsonTemplate.description.width,
        jsonTemplate.description.origin_x,
        jsonTemplate.description.origin_y,
        jsonTemplate.price.height,
        jsonTemplate.price.width,
        jsonTemplate.price.origin_x,
        jsonTemplate.price.origin_y,
        jsonTemplate.logo.height,
        jsonTemplate.logo.width,
        jsonTemplate.logo.origin_x,
        jsonTemplate.logo.origin_y,
        jsonTemplate.callToAction.height,
        jsonTemplate.callToAction.width,
        jsonTemplate.callToAction.origin_x,
        jsonTemplate.callToAction.origin_y,
        jsonTemplate.main.height,
        jsonTemplate.main.width,
        jsonTemplate.name
    );
    return bb;
  }

  public List<String> getImagesByTemplate(JsonCreativeAsset jsonCreativeAsset,
                                          String uploadedproductImageFile,
                                          String uploadedlogoImageFile,
                                          String uploadedcallToActionImageFile)
      throws Exception {
    List<String> listTemplates = new ArrayList<>();
    String imageString = null;
    for (JsonTemplate jsonTemplate : jsonCreativeAsset.jsonTemplates) {
      AdCreator adCreator = new AdCreator();
      //this should repeat several times for list of templates
      ByteBuffer
          bb =
          generatePreview(jsonCreativeAsset, jsonTemplate, uploadedproductImageFile,
                          uploadedlogoImageFile,
                          uploadedcallToActionImageFile);

      adCreator.setId(counter++);
      adCreator.setImageUrl("/adcreator/" + adCreator.getId() + ".jpg");
      Base64.Encoder encoder = Base64.getEncoder();
      imageString = encoder.encodeToString(bb.array());
      listTemplates.add(imageString);
    }

    return listTemplates;
  }

  public String writeToFile(InputStream inputStream, String fileName) {
    File f = null;
    try {
      f = File.createTempFile(fileName, "", null);
      OutputStream outputStream = new FileOutputStream(f);
      IOUtils.copy(inputStream, outputStream);
      outputStream.close();
    } catch (IOException e) {
      logger.error("IOException " + e);
    }

    return f.getAbsolutePath();
  }

}
