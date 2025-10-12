package com.example.contract.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.*;

/**
 * TokenRequest
 */

public class TokenRequest {

  private String clientId;

  public TokenRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TokenRequest(String clientId) {
    this.clientId = clientId;
  }

  public TokenRequest clientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  /**
   * The registered client identifier
   * @return clientId
  */
  @NotNull @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$") 
  @Schema(name = "client_id", description = "The registered client identifier", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("client_id")
  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TokenRequest tokenRequest = (TokenRequest) o;
    return Objects.equals(this.clientId, tokenRequest.clientId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TokenRequest {\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
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

