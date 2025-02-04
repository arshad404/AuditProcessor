package com.phonepe.payments.fundtransfer.codec;

import java.util.Arrays;
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
  private String queryParams;

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
    sb.append("Headers: ").append(headers).append("\n");
    sb.append("URL: ").append(url).append("\n");
    sb.append("Query Params: ").append(queryParams).append("\n");

    // Encoder
    sb.append("Request Data (byte array): ").append(Arrays.toString(requestData)).append("\n");

    // Decoder
    sb.append("Response Data (byte array): ").append(Arrays.toString(responseData)).append("\n");

    // Response Interceptor
    sb.append("Status Code: ").append(statusCode).append("\n");
    sb.append("Reason: ").append(reason).append("\n");
    sb.append("Error Body: ").append(errorBody).append("\n");

    return sb.toString();
  }

}
