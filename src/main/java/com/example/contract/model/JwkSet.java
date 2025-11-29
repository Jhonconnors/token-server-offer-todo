package com.example.contract.model;

import java.util.Objects;
import com.example.contract.model.JwkSetKeysInner;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.*;

/**
 * JwkSet
 */

public class JwkSet {

  @Valid
  private List<@Valid JwkSetKeysInner> keys = new ArrayList<>();

  public JwkSet keys(List<@Valid JwkSetKeysInner> keys) {
    this.keys = keys;
    return this;
  }

  public JwkSet addKeysItem(JwkSetKeysInner keysItem) {
    if (this.keys == null) {
      this.keys = new ArrayList<>();
    }
    this.keys.add(keysItem);
    return this;
  }

  /**
   * Get keys
   * @return keys
  */
  @Valid 
  @Schema(name = "keys", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("keys")
  public List<@Valid JwkSetKeysInner> getKeys() {
    return keys;
  }

  public void setKeys(List<@Valid JwkSetKeysInner> keys) {
    this.keys = keys;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JwkSet jwkSet = (JwkSet) o;
    return Objects.equals(this.keys, jwkSet.keys);
  }

  @Override
  public int hashCode() {
    return Objects.hash(keys);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JwkSet {\n");
    sb.append("    keys: ").append(toIndentedString(keys)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

