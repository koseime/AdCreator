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

import javax.ws.rs.core.CacheControl;

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

  private ByteBuffer generatePreview (CreativeAsset creativeAsset, Template template, String uploadedproductImageFile, String uploadedlogoImageFile, String uploadedcallToActionImageFile)
      throws Exception {
    ByteBuffer bb = client.generate(
        creativeAsset.getTitle(),
        creativeAsset.getBody(),
        "price",
        uploadedproductImageFile,
        uploadedlogoImageFile,
        uploadedcallToActionImageFile,
        creativeAsset.getTitleFont(),
        creativeAsset.getTitleFontSize(),
        creativeAsset.getTitleFontWeight(),
        creativeAsset.getTitleFontColor(),
        creativeAsset.getPriceFont(),
        creativeAsset.getPriceFontSize(),
        Integer.parseInt(creativeAsset.getPriceFontWeight()),
        creativeAsset.getPriceFontColor(),
        creativeAsset.getBodyFont(),
        creativeAsset.getBodyFontSize(),
        Integer.parseInt(creativeAsset.getBodyFontWeight()),
        creativeAsset.getBodyFontColor(),
        creativeAsset.getBackgroundColor(),
        "",
        template.getProduct().getHeight(),
        template.getProduct().getWidth(),
        template.getProduct().getOrigin_x(),
        template.getProduct().getOrigin_y(),
        template.getTitle().getHeight(),
        template.getTitle().getWidth(),
        template.getTitle().getOrigin_x(),
        template.getTitle().getOrigin_y(),
        template.getDescription().getHeight(),
        template.getDescription().getWidth(),
        template.getDescription().getOrigin_x(),
        template.getDescription().getOrigin_y(),
        template.getPrice().getHeight(),
        template.getPrice().getWidth(),
        template.getPrice().getOrigin_x(),
        template.getPrice().getOrigin_y(),
        template.getLogo().getHeight(),
        template.getLogo().getWidth(),
        template.getLogo().getOrigin_x(),
        template.getLogo().getOrigin_y(),
        template.getCallToAction().getHeight(),
        template.getCallToAction().getWidth(),
        template.getCallToAction().getOrigin_x(),
        template.getCallToAction().getOrigin_y(),
        template.getMain().getHeight(),
        template.getMain().getWidth(),
        template.getName()
    );
    return bb;
  }

  public List<String> getImagesByTemplate(CreativeAsset creativeAsset,
                                          String uploadedproductImageFile,
                                          String uploadedlogoImageFile,
                                          String uploadedcallToActionImageFile)
      throws Exception {
    List<AdCreator> adCreatorList = new ArrayList<>();
    for (Template template : creativeAsset.getTemplates()) {
      AdCreator adCreator = new AdCreator();
      //this should repeat several times for list of templates
      ByteBuffer
          bb =
          generatePreview(creativeAsset, template, uploadedproductImageFile,
                          uploadedlogoImageFile,
                          uploadedcallToActionImageFile);

      adCreator.setId(counter++);
      cache.put(adCreator.getId() + "", bb);
      adCreatorList.add(adCreator);
    }
    String imageString = null;
    List<String> listTemplates = new ArrayList<>();
    for (AdCreator creator : adCreatorList) {
      ByteBuffer buff = cache.get(creator.getImageUrl());
      Base64.Encoder encoder = Base64.getEncoder();
      imageString = encoder.encodeToString(buff.array());
      listTemplates.add(imageString);
      System.out.println(imageString);
    }

    CacheControl cc = new CacheControl();
    cc.setNoTransform(true);
    cc.setMustRevalidate(false);
    cc.setNoCache(false);
    cc.setMaxAge(1);

    return listTemplates;
  }

}
