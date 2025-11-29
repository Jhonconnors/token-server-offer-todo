package com.example.contract.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.*;

/**
 * TokenAssertionRequest
 */

public class TokenAssertionRequest {

  private String clientId;

  private String clientAssertion;

  public TokenAssertionRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TokenAssertionRequest(String clientId, String clientAssertion) {
    this.clientId = clientId;
    this.clientAssertion = clientAssertion;
  }

  public TokenAssertionRequest clientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  /**
   * The registered client identifier
   * @return clientId
  */
  @NotNull 
  @Schema(name = "client_id", description = "The registered client identifier", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("client_id")
  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public TokenAssertionRequest clientAssertion(String clientAssertion) {
    this.clientAssertion = clientAssertion;
    return this;
  }

  /**
   * JWT signed by the client (as per RFC 7523)
   * @return clientAssertion
  */
  @NotNull 
  @Schema(name = "client_assertion", description = "JWT signed by the client (as per RFC 7523)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("client_assertion")
  public String getClientAssertion() {
    return clientAssertion;
  }

  public void setClientAssertion(String clientAssertion) {
    this.clientAssertion = clientAssertion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TokenAssertionRequest tokenAssertionRequest = (TokenAssertionRequest) o;
    return Objects.equals(this.clientId, tokenAssertionRequest.clientId) &&
        Objects.equals(this.clientAssertion, tokenAssertionRequest.clientAssertion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, clientAssertion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TokenAssertionRequest {\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    clientAssertion: ").append(toIndentedString(clientAssertion)).append("\n");
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

