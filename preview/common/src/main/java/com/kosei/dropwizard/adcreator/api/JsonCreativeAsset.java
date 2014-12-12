package com.kosei.dropwizard.adcreator.api;

import com.google.common.base.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


/**
 * Created by elisaveta.manasieva on 12/5/2014.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonCreativeAsset {

  public final String backgroundColor;
  public final String body;
  public final String bodyFont;
  public final String bodyFontColor;
  public final String bodyFontWeight;
  public final String bodyFontType;
  public final int bodyFontSize;
  public final String price;
  public final String priceFont;
  public final String priceFontColor;
  public final String priceFontWeight;
  public final String priceFontType;
  public final int priceFontSize;
  public final String title;
  public final String titleFont;
  public final String titleFontColor;
  public final int titleFontWeight;
  public final String titleFontType;
  public final int titleFontSize;
  public final List<JsonTemplate> jsonTemplates;

  @JsonCreator
  public JsonCreativeAsset(@JsonProperty("backgroundColor") String backgroundColor,
                           @JsonProperty("body") String body,
                           @JsonProperty("bodyFont") String bodyFont,
                           @JsonProperty("bodyFontColor") String bodyFontColor,
                           @JsonProperty("bodyFontWeight") String bodyFontWeight,
                           @JsonProperty("bodyFontType") String bodyFontType,
                           @JsonProperty("bodyFontSize") int bodyFontSize,
                           @JsonProperty("price") String price,
                           @JsonProperty("priceFont") String priceFont,
                           @JsonProperty("priceFontColor") String priceFontColor,
                           @JsonProperty("priceFontWeight") String priceFontWeight,
                           @JsonProperty("priceFontType") String priceFontType,
                           @JsonProperty("priceFontSize") int priceFontSize,
                           @JsonProperty("title") String title,
                           @JsonProperty("titleFont") String titleFont,
                           @JsonProperty("titleFontColor") String titleFontColor,
                           @JsonProperty("titleFontWeight") int titleFontWeight,
                           @JsonProperty("titleFontType") String titleFontType,
                           @JsonProperty("titleFontSize") int titleFontSize,
                           @JsonProperty("templates") List<JsonTemplate> jsonTemplates) {
    this.backgroundColor = backgroundColor;
    this.body = body;
    this.bodyFont = bodyFont;
    this.bodyFontColor = bodyFontColor;
    this.bodyFontWeight = bodyFontWeight;
    this.bodyFontType = bodyFontType;
    this.bodyFontSize = bodyFontSize;
    this.price = price;
    this.priceFont = priceFont;
    this.priceFontColor = priceFontColor;
    this.priceFontWeight = priceFontWeight;
    this.priceFontType = priceFontType;
    this.priceFontSize = priceFontSize;
    this.title = title;
    this.titleFont = titleFont;
    this.titleFontColor = titleFontColor;
    this.titleFontWeight = titleFontWeight;
    this.titleFontType = titleFontType;
    this.titleFontSize = titleFontSize;
    this.jsonTemplates = jsonTemplates;
  }

  @Override
  public int hashCode() {
    return Objects
        .hashCode(
                 backgroundColor, body, bodyFont,
                  bodyFontColor, bodyFontWeight, bodyFontType, bodyFontSize, price, priceFont,
                  priceFontColor, priceFontWeight, priceFontType, priceFontSize, title, titleFont,
                  titleFontColor, titleFontWeight, titleFontType, titleFontSize, jsonTemplates);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final JsonCreativeAsset other = (JsonCreativeAsset) obj;
    return Objects
               .equal(this.backgroundColor, other.backgroundColor) && Objects
               .equal(this.body, other.body) && Objects.equal(this.bodyFont, other.bodyFont)
           && Objects.equal(this.bodyFontColor, other.bodyFontColor) && Objects
        .equal(this.bodyFontWeight, other.bodyFontWeight) && Objects
               .equal(this.bodyFontType, other.bodyFontType) && Objects
               .equal(this.bodyFontSize, other.bodyFontSize) && Objects
        .equal(this.price, other.price)
           && Objects.equal(this.priceFont, other.priceFont)
           && Objects.equal(this.priceFontColor, other.priceFontColor) && Objects
        .equal(this.priceFontWeight, other.priceFontWeight) && Objects
               .equal(this.priceFontType, other.priceFontType) && Objects
               .equal(this.priceFontSize, other.priceFontSize) && Objects
               .equal(this.title, other.title) && Objects.equal(this.titleFont, other.titleFont)
           && Objects.equal(this.titleFontColor, other.titleFontColor) && Objects
        .equal(this.titleFontWeight, other.titleFontWeight) && Objects
               .equal(this.titleFontType, other.titleFontType) && Objects
               .equal(this.titleFontSize, other.titleFontSize)&& Objects
               .equal(this.jsonTemplates, other.jsonTemplates);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("backgroundColor", backgroundColor)
        .add("body", body)
        .add("bodyFont", bodyFont)
        .add("bodyFontColor", bodyFontColor)
        .add("bodyFontWeight", bodyFontWeight)
        .add("bodyFontType", bodyFontType)
        .add("bodyFontSize", bodyFontSize)
        .add("price", price)
        .add("priceFont", priceFont)
        .add("priceFontColor", priceFontColor)
        .add("priceFontWeight", priceFontWeight)
        .add("priceFontType", priceFontType)
        .add("priceFontSize", priceFontSize)
        .add("title", title)
        .add("titleFont", titleFont)
        .add("titleFontColor", titleFontColor)
        .add("titleFontWeight", titleFontWeight)
        .add("titleFontType", titleFontType)
        .add("titleFontSize", titleFontSize)
        .add("templates", jsonTemplates)
        .toString();
  }
}
