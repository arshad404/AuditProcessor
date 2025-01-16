package com.phonepe.payments.fundtransfer.codec;

import feign.Response;

public class AuditException extends RuntimeException {

  private Object exceptionData;

  private Response response;

  private String methodKey;

  public AuditException(Object exceptionData, Response response, String methodKey) {
    this.exceptionData = exceptionData;
    this.response = response;
    this.methodKey = methodKey;
  }

  public Object getExceptionData() {
    return exceptionData;
  }

  public Response getResponse() {
    return response;
  }

  public String getMethodKey() {
    return methodKey;
  }
 }
