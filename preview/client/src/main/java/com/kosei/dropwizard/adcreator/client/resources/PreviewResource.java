package com.kosei.dropwizard.adcreator.client.resources;

import com.kosei.dropwizard.adcreator.api.JsonCreativeAsset;

import resources.PreviewResourceConstants;
import resources.ResourceConstants;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

import javax.ws.rs.core.Response;

/**
 *
 */
public interface PreviewResource {

  @POST(ResourceConstants.CONTEXT_PATH + PreviewResourceConstants.BASE_PATH)
  @Multipart
  Response createAdCreator(
      @Part("creativeAsset") JsonCreativeAsset creativeAsset,
      @Part("product") TypedFile product,
      @Part("logo") TypedFile logo,
      @Part("callToAction") TypedFile callToAction
  );
}
