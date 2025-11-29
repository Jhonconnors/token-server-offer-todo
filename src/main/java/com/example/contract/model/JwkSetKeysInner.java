package com.example.contract.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.*;

/**
 * JwkSetKeysInner
 */

@JsonTypeName("JwkSet_keys_inner")
public class JwkSetKeysInner {

  private String kty;

  private String kid;

  private String use;

  private String alg;

  private String n;

  private String e;

  public JwkSetKeysInner kty(String kty) {
    this.kty = kty;
    return this;
  }

  /**
   * Get kty
   * @return kty
  */

  @Schema(name = "kty", example = "RSA", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("kty")
  public String getKty() {
    return kty;
  }

  public void setKty(String kty) {
    this.kty = kty;
  }

  public JwkSetKeysInner kid(String kid) {
    this.kid = kid;
    return this;
  }

  /**
   * Get kid
   * @return kid
  */

  @Schema(name = "kid", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("kid")
  public String getKid() {
    return kid;
  }

  public void setKid(String kid) {
    this.kid = kid;
  }

  public JwkSetKeysInner use(String use) {
    this.use = use;
    return this;
  }

  /**
   * Get use
   * @return use
  */

  @Schema(name = "use", example = "sig", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("use")
  public String getUse() {
    return use;
  }

  public void setUse(String use) {
    this.use = use;
  }

  public JwkSetKeysInner alg(String alg) {
    this.alg = alg;
    return this;
  }

  /**
   * Get alg
   * @return alg
  */

  @Schema(name = "alg", example = "RS512", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("alg")
  public String getAlg() {
    return alg;
  }

  public void setAlg(String alg) {
    this.alg = alg;
  }

  public JwkSetKeysInner n(String n) {
    this.n = n;
    return this;
  }

  /**
   * Get n
   * @return n
  */

  @Schema(name = "n", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("n")
  public String getN() {
    return n;
  }

  public void setN(String n) {
    this.n = n;
  }

  public JwkSetKeysInner e(String e) {
    this.e = e;
    return this;
  }

  /**
   * Get e
   * @return e
  */

  @Schema(name = "e", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("e")
  public String getE() {
    return e;
  }

  public void setE(String e) {
    this.e = e;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JwkSetKeysInner jwkSetKeysInner = (JwkSetKeysInner) o;
    return Objects.equals(this.kty, jwkSetKeysInner.kty) &&
        Objects.equals(this.kid, jwkSetKeysInner.kid) &&
        Objects.equals(this.use, jwkSetKeysInner.use) &&
        Objects.equals(this.alg, jwkSetKeysInner.alg) &&
        Objects.equals(this.n, jwkSetKeysInner.n) &&
        Objects.equals(this.e, jwkSetKeysInner.e);
  }

  @Override
  public int hashCode() {
    return Objects.hash(kty, kid, use, alg, n, e);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JwkSetKeysInner {\n");
    sb.append("    kty: ").append(toIndentedString(kty)).append("\n");
    sb.append("    kid: ").append(toIndentedString(kid)).append("\n");
    sb.append("    use: ").append(toIndentedString(use)).append("\n");
    sb.append("    alg: ").append(toIndentedString(alg)).append("\n");
    sb.append("    n: ").append(toIndentedString(n)).append("\n");
    sb.append("    e: ").append(toIndentedString(e)).append("\n");
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

