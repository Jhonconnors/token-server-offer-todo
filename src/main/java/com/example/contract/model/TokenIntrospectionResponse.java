package com.example.contract.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.*;

/**
 * TokenIntrospectionResponse
 */

public class TokenIntrospectionResponse {

  private Boolean active;

  private String scope;

  private String clientId;

  private Integer exp;

  public TokenIntrospectionResponse active(Boolean active) {
    this.active = active;
    return this;
  }

  /**
   * Get active
   * @return active
  */

  @Schema(name = "active", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("active")
  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public TokenIntrospectionResponse scope(String scope) {
    this.scope = scope;
    return this;
  }

  /**
   * Get scope
   * @return scope
  */

  @Schema(name = "scope", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("scope")
  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public TokenIntrospectionResponse clientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  /**
   * Get clientId
   * @return clientId
  */

  @Schema(name = "client_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("client_id")
  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public TokenIntrospectionResponse exp(Integer exp) {
    this.exp = exp;
    return this;
  }

  /**
   * Get exp
   * @return exp
  */

  @Schema(name = "exp", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("exp")
  public Integer getExp() {
    return exp;
  }

  public void setExp(Integer exp) {
    this.exp = exp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TokenIntrospectionResponse tokenIntrospectionResponse = (TokenIntrospectionResponse) o;
    return Objects.equals(this.active, tokenIntrospectionResponse.active) &&
        Objects.equals(this.scope, tokenIntrospectionResponse.scope) &&
        Objects.equals(this.clientId, tokenIntrospectionResponse.clientId) &&
        Objects.equals(this.exp, tokenIntrospectionResponse.exp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(active, scope, clientId, exp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TokenIntrospectionResponse {\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
    sb.append("    scope: ").append(toIndentedString(scope)).append("\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    exp: ").append(toIndentedString(exp)).append("\n");
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

