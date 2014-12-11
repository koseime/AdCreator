package com.kosei.dropwizard.adcreator.api;

/**
 * Created by elisaveta.manasieva on 12/10/2014.
 */
public class Template {


  private Parameters product;
  private Parameters logo;
  private Parameters title;
  private Parameters price;
  private Parameters description;
  private String name;
  private Parameters callToAction;
  private Parameters main;

  public Parameters getProduct() {
    return product;
  }

  public void setProduct(Parameters product) {
    this.product = product;
  }

  public Parameters getLogo() {
    return logo;
  }

  public void setLogo(Parameters logo) {
    this.logo = logo;
  }

  public Parameters getTitle() {
    return title;
  }

  public void setTitle(Parameters title) {
    this.title = title;
  }

  public Parameters getDescription() {
    return description;
  }

  public void setDescription(Parameters description) {
    this.description = description;
  }

  public Parameters getPrice() {
    return price;
  }

  public void setPrice(Parameters price) {
    this.price = price;
  }

  public Parameters getCallToAction() {
    return callToAction;
  }

  public void setCallToAction(Parameters callToAction) {
    this.callToAction = callToAction;
  }

  public Parameters getMain() {
    return main;
  }

  public void setMain(Parameters main) {
    this.main = main;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static class Parameters {

    private int height;
    private int width;
    private int origin_x;
    private int origin_y;

    public int getHeight() {
      return height;
    }

    public void setHeight(int height) {
      this.height = height;
    }

    public int getWidth() {
      return width;
    }

    public void setWidth(int width) {
      this.width = width;
    }

    public int getOrigin_x() {
      return origin_x;
    }

    public void setOrigin_x(int origin_x) {
      this.origin_x = origin_x;
    }

    public int getOrigin_y() {
      return origin_y;
    }

    public void setOrigin_y(int origin_y) {
      this.origin_y = origin_y;
    }
  }

}

