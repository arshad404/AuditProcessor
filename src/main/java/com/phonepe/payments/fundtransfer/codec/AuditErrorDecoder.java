package com.phonepe.payments.fundtransfer.codec;

import feign.Response;
import feign.codec.ErrorDecoder;

public class AuditErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String s, Response response) {
    int statusCode = response.status();
    if(statusCode >= 400 && statusCode < 500) {
      return new Custom4xxAuditErrorException(String.valueOf(statusCode), new Exception(response.reason()));
    }

    if(statusCode >= 500) {
      return new Custom5xxAuditErrorException(String.valueOf(statusCode), new Exception(response.reason()));
    }

    return new Exception("Unexpected error occurred with status code: " + statusCode);
  }
}
