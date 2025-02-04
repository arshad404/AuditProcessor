package com.phonepe.payments.fundtransfer.codec;

import static java.util.Objects.nonNull;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DefaultAuditContext implements AuditContext {

  private String id;

  // Request Interceptor
  private String method;
  private Map<String, Collection<String>> headers;
  private String url;
  private Map<String, Collection<String>> queryParams;

  // Encoder
  private byte[] requestData;

  // Decoder
  private byte[] responseData;

  // Response Interceptor
  private int statusCode;
  private String reason;
  private String errorBody;

  @Override
  public String getId() {
    return UUID.randomUUID().toString();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ID: ").append(id).append("\n");
    // Request Interceptor
    sb.append("Method: ").append(method).append("\n");
    if (!headers.isEmpty()) {
      sb.append("Headers: ").append(headers).append("\n");
    }
    sb.append("URL: ").append(url).append("\n");
    if (!queryParams.isEmpty()) {
      sb.append("Query Params: ").append(queryParams).append("\n");
    }
    if (nonNull(requestData) && requestData.length > 0) {
      sb.append("Request Data (byte array as String): ")
          .append(new String(requestData))
          .append("\n");
    }
    if (nonNull(responseData) && responseData.length > 0) {
      sb.append("Response Data (byte array as String): ")
          .append(new String(responseData))
          .append("\n");
    }
    // Response Interceptor
    sb.append("Status Code: ").append(statusCode).append("\n");
    sb.append("Reason: ").append(reason).append("\n");
    if (nonNull(errorBody) && !errorBody.isEmpty()) {
      sb.append("Error Body: ")
          .append(errorBody)
          .append("\n");
    }
    return sb.toString();
  }
}
