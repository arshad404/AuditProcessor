package com.phonepe.payments.fundtransfer.codec;

import java.util.Collection;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiException extends RuntimeException {

  private final String methodKey;
  private final int statusCode;
  private final String reason;
  private final Map<String, Collection<String>> headers;
  private final String responseBody;

  public ApiException(String methodKey, int statusCode, String reason,
      Map<String, Collection<String>> headers, String responseBody) {
    super(String.format("API call failed [%s]: %d %s - Response: %s",
        methodKey, statusCode, reason, responseBody));
    this.methodKey = methodKey;
    this.statusCode = statusCode;
    this.reason = reason;
    this.headers = headers;
    this.responseBody = responseBody;
  }
}

