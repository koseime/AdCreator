package com.kosei.dropwizard.adcreator.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.NotBlank;

public class AdCreatorClientConfig {
    @JsonProperty
    @NotBlank
    public final String apiEndpoint;


    @JsonCreator
    public AdCreatorClientConfig(
        @JsonProperty("apiEndpoint") String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }
}