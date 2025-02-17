package com.phonepe.payments.fundtransfer.codec;

import feign.RequestTemplate;
import feign.Response;
import java.lang.reflect.Type;

public class DefaultTransformer {

  @AuditTransformer(name = "requestTransformer")
  public Object transformRequest(Object o, Type type, RequestTemplate requestTemplate) {
    return o;
  }

  @AuditTransformer(name = "responseTransformer")
  public Object transformResponse(Object o, Type type, Response response) {
    return o;
  }

  @AuditTransformer(name = "loggerTransformer")
  public Response transformResponseInLogger(Response response) {
    return response;
  }

}
