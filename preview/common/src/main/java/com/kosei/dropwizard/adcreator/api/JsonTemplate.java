package com.kosei.dropwizard.adcreator.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by elisaveta.manasieva on 12/10/2014.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonTemplate {


  public final JsonParameter product;
  public final JsonParameter logo;
  public final JsonParameter title;
  public final JsonParameter price;
  public final JsonParameter description;
  public final String name;
  public final JsonParameter callToAction;
  public final JsonParameter main;

  @JsonCreator
  public JsonTemplate(@JsonProperty("product") JsonParameter product,
                      @JsonProperty("logo") JsonParameter logo,
                      @JsonProperty("title") JsonParameter title,
                      @JsonProperty("price") JsonParameter price,
                      @JsonProperty("description") JsonParameter description,
                      @JsonProperty("name") String name,
                      @JsonProperty("callToAction") JsonParameter callToAction,
                      @JsonProperty("main") JsonParameter main) {
    this.product = product;
    this.logo = logo;
    this.title = title;
    this.price = price;
    this.description = description;
    this.name = name;
    this.callToAction = callToAction;
    this.main = main;
  }
}

