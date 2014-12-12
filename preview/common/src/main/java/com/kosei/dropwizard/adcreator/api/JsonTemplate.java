package com.kosei.dropwizard.adcreator.api;

import com.google.common.base.Objects;

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


  public final Parameters product;
  public final Parameters logo;
  public final Parameters title;
  public final Parameters price;
  public final Parameters description;
  public final String name;
  public final Parameters callToAction;
  public final Parameters main;

  @JsonCreator
  public JsonTemplate(@JsonProperty("product") Parameters product,
                      @JsonProperty("logo") Parameters logo,
                      @JsonProperty("title") Parameters title,
                      @JsonProperty("price") Parameters price,
                      @JsonProperty("description") Parameters description,
                      @JsonProperty("name") String name,
                      @JsonProperty("callToAction") Parameters callToAction,
                      @JsonProperty("main") Parameters main) {
    this.product = product;
    this.logo = logo;
    this.title = title;
    this.price = price;
    this.description = description;
    this.name = name;
    this.callToAction = callToAction;
    this.main = main;
  }


  public class Parameters {

    public final int height;
    public final int width;
    public final int origin_x;
    public final int origin_y;

    @JsonCreator
    public Parameters(@JsonProperty("height") int height,
                    @JsonProperty("width") int width,
                    @JsonProperty("origin_x") int origin_x,
                    @JsonProperty("origin_y") int origin_y) {
      this.height = height;
      this.width = width;
      this.origin_x = origin_x;
      this.origin_y = origin_y;
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(height, width, origin_x, origin_y);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      final Parameters other = (Parameters) obj;
      return Objects.equal(this.height, other.height) && Objects.equal(this.width, other.width)
             && Objects.equal(this.origin_x, other.origin_x) && Objects
          .equal(this.origin_y, other.origin_y);
    }
  }

}
