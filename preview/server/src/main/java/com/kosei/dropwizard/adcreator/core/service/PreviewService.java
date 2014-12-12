package com.kosei.dropwizard.adcreator.core.service;

import com.kosei.dropwizard.adcreator.api.CreativeAsset;
import com.kosei.dropwizard.adcreator.api.Template;
import com.kosei.dropwizard.adcreator.core.AdCreator;
import com.kosei.dropwizard.adcreator.core.AdPreviewCreatorClient;
import com.kosei.dropwizard.adcreator.core.CreatedImageCache;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by elisaveta.manasieva on 12/11/2014.
 */
public class PreviewService {

  private final AdPreviewCreatorClient client;
  private final CreatedImageCache cache;
  private static int counter = 0;

  public PreviewService(AdPreviewCreatorClient client, CreatedImageCache cache) {
    this.client = client;
    this.cache = cache;
  }

  private ByteBuffer generatePreview(CreativeAsset creativeAsset, Template template,
                                     String uploadedproductImageFile, String uploadedlogoImageFile,
                                     String uploadedcallToActionImageFile)
      throws Exception {
    ByteBuffer bb = client.generate(
        creativeAsset.title,
        creativeAsset.body,
        "price",
        uploadedproductImageFile,
        uploadedlogoImageFile,
        uploadedcallToActionImageFile,
        creativeAsset.titleFont,
        creativeAsset.titleFontSize,
        creativeAsset.titleFontWeight,
        creativeAsset.titleFontColor,
        creativeAsset.priceFont,
        creativeAsset.priceFontSize,
        Integer.parseInt(creativeAsset.priceFontWeight),
        creativeAsset.priceFontColor,
        creativeAsset.bodyFont,
        creativeAsset.bodyFontSize,
        Integer.parseInt(creativeAsset.bodyFontWeight),
        creativeAsset.bodyFontColor,
        creativeAsset.backgroundColor,
        "",
        template.product.height,
        template.product.width,
        template.product.origin_x,
        template.product.origin_y,
        template.title.height,
        template.title.width,
        template.title.origin_x,
        template.title.origin_y,
        template.description.height,
        template.description.width,
        template.description.origin_x,
        template.description.origin_y,
        template.price.height,
        template.price.width,
        template.price.origin_x,
        template.price.origin_y,
        template.logo.height,
        template.logo.width,
        template.logo.origin_x,
        template.logo.origin_y,
        template.callToAction.height,
        template.callToAction.width,
        template.callToAction.origin_x,
        template.callToAction.origin_y,
        template.main.height,
        template.main.width,
        template.name
    );
    return bb;
  }

  public List<String> getImagesByTemplate(CreativeAsset creativeAsset,
                                          String uploadedproductImageFile,
                                          String uploadedlogoImageFile,
                                          String uploadedcallToActionImageFile)
      throws Exception {
    List<String> listTemplates = new ArrayList<>();
    String imageString = null;
    for (Template template : creativeAsset.templates) {
      AdCreator adCreator = new AdCreator();
      //this should repeat several times for list of templates
      ByteBuffer
          bb =
          generatePreview(creativeAsset, template, uploadedproductImageFile,
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

}
