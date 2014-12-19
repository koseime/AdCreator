package com.kosei.dropwizard.adcreator.api;

import com.google.common.base.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class JsonParameter {
  public final int height;
  public final int width;
  public final int origin_x;
  public final int origin_y;

  @JsonCreator
  public JsonParameter(@JsonProperty("height") int height,
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
    final JsonParameter other = (JsonParameter) obj;
    return Objects.equal(this.height, other.height) && Objects.equal(this.width, other.width)
           && Objects.equal(this.origin_x, other.origin_x) && Objects
        .equal(this.origin_y, other.origin_y);
  }
}
