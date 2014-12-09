package com.kosei.dropwizard.adcreator.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by elisaveta.manasieva on 12/5/2014.
 */
public class CreativeAsset {

  @NotBlank
  private String backgroundColor;
  @NotNull
  private String body;
  @NotBlank
  private String bodyFont;
  @NotBlank
  private String bodyFontColor;
  @NotBlank
  private String bodyFontWeight;
  @NotBlank
  private String bodyFontType;
  @Min(0)
  private int bodyFontSize;
  @NotBlank
  private String priceFont;
  @NotBlank
  private String priceFontColor;
  @NotBlank
  private String priceFontWeight;
  @NotBlank
  private String priceFontType;
  @Min(0)
  private int priceFontSize;
  @NotNull
  private String title;
  @NotBlank
  private String titleFont;
  @NotBlank
  private String titleFontColor;
  @NotBlank
  private String titleFontWeight;
  @NotBlank
  private String titleFontType;
  @Min(0)
  private int titleFontSize;
  private List<String> templates;
  public CreativeAsset() {
  }

  public CreativeAsset(

                       String logoLocation, String callToActionLocation, String backgroundColor,
                       String body, String bodyFont, String bodyFontColor, String bodyFontWeight,
                       String bodyFontType,int bodyFontSize,
                       String priceFont, String priceFontColor, String priceFontWeight,
                       String priceFontType,int priceFontSize,
                       String title, String titleFont, String titleFontColor,
                       String titleFontWeight,
                       String titleFontType, int titleFontSize, List<String> templates) {
    this.backgroundColor = backgroundColor;
    this.body = body;
    this.bodyFont = bodyFont;
    this.bodyFontColor = bodyFontColor;
    this.bodyFontWeight = bodyFontWeight;
    this.bodyFontType = bodyFontType;
    this.bodyFontSize = bodyFontSize;
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
    this.templates = templates;
  }

  public String getBackgroundColor() {
    return backgroundColor;
  }

  public void setBackgroundColor(String backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getBodyFont() {
    return bodyFont;
  }

  public void setBodyFont(String bodyFont) {
    this.bodyFont = bodyFont;
  }

  public String getBodyFontColor() {
    return bodyFontColor;
  }

  public void setBodyFontColor(String bodyFontColor) {
    this.bodyFontColor = bodyFontColor;
  }

  public String getBodyFontWeight() {
    return bodyFontWeight;
  }

  public void setBodyFontWeight(String bodyFontWeight) {
    this.bodyFontWeight = bodyFontWeight;
  }

  public String getBodyFontType() {
    return bodyFontType;
  }

  public void setBodyFontType(String bodyFontType) {
    this.bodyFontType = bodyFontType;
  }

  public int getBodyFontSize() {
    return bodyFontSize;
  }

  public void setBodyFontSize(int bodyFontSize) {
    this.bodyFontSize = bodyFontSize;
  }

  public String getPriceFont() {
    return priceFont;
  }

  public void setPriceFont(String priceFont) {
    this.priceFont = priceFont;
  }

  public String getPriceFontColor() {
    return priceFontColor;
  }

  public void setPriceFontColor(String priceFontColor) {
    this.priceFontColor = priceFontColor;
  }

  public String getPriceFontWeight() {
    return priceFontWeight;
  }

  public void setPriceFontWeight(String priceFontWeight) {
    this.priceFontWeight = priceFontWeight;
  }

  public String getPriceFontType() {
    return priceFontType;
  }

  public void setPriceFontType(String priceFontType) {
    this.priceFontType = priceFontType;
  }

  public int getPriceFontSize() {
    return priceFontSize;
  }

  public void setPriceFontSize(int priceFontSize) {
    this.priceFontSize = priceFontSize;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitleFont() {
    return titleFont;
  }

  public void setTitleFont(String titleFont) {
    this.titleFont = titleFont;
  }

  public String getTitleFontColor() {
    return titleFontColor;
  }

  public void setTitleFontColor(String titleFontColor) {
    this.titleFontColor = titleFontColor;
  }

  public String getTitleFontWeight() {
    return titleFontWeight;
  }

  public void setTitleFontWeight(String titleFontWeight) {
    this.titleFontWeight = titleFontWeight;
  }

  public String getTitleFontType() {
    return titleFontType;
  }

  public void setTitleFontType(String titleFontType) {
    this.titleFontType = titleFontType;
  }

  public int getTitleFontSize() {
    return titleFontSize;
  }

  public void setTitleFontSize(int titleFontSize) {
    this.titleFontSize = titleFontSize;
  }

  public List<String> getTemplates() {
    return templates;
  }

  public void setTemplates(List<String> templates) {
    this.templates = templates;
  }

  @Override
  public int hashCode() {
    return Objects
        .hashCode(
                 backgroundColor, body, bodyFont,
                  bodyFontColor, bodyFontWeight, bodyFontType, bodyFontSize, priceFont,
                  priceFontColor, priceFontWeight, priceFontType, priceFontSize, title, titleFont,
                  titleFontColor, titleFontWeight, titleFontType, titleFontSize, templates);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final CreativeAsset other = (CreativeAsset) obj;
    return Objects
               .equal(this.backgroundColor, other.backgroundColor) && Objects
               .equal(this.body, other.body) && Objects.equal(this.bodyFont, other.bodyFont)
           && Objects.equal(this.bodyFontColor, other.bodyFontColor) && Objects
        .equal(this.bodyFontWeight, other.bodyFontWeight) && Objects
               .equal(this.bodyFontType, other.bodyFontType) && Objects
               .equal(this.bodyFontSize, other.bodyFontSize)
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
               .equal(this.templates, other.templates);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("backgroundColor", backgroundColor)
        .add("body", body)
        .add("bodyFont", bodyFont)
        .add("bodyFontColor", bodyFontColor)
        .add("bodyFontWeight", bodyFontWeight)
        .add("bodyFontType", bodyFontType)
        .add("bodyFontSize", bodyFontSize)
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
        .add("templates", templates)
        .toString();
  }
}
