package com.kosei.dropwizard.adcreator.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosei.common.client.json.JacksonJsonConverter;
import com.kosei.dropwizard.adcreator.client.resources.PreviewResource;

import retrofit.RestAdapter;
import retrofit.client.Client;

public class AdCreatorJsonClient {

  private final PreviewResource previewResource;

  public AdCreatorJsonClient(final String endpoint,
                             final ObjectMapper objectMapper, final Client client) {
    RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint(endpoint)
        .setConverter(new JacksonJsonConverter(objectMapper))
//        .setLogLevel(RestAdapter.LogLevel.FULL)
        .setClient(client)
        .build();
    previewResource = restAdapter.create(PreviewResource.class);
  }

  public PreviewResource getPreviewResource() {
    return previewResource;
  }
}
