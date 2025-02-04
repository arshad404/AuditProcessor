package com.phonepe.payments.fundtransfer.codec;

import feign.RequestTemplate;
import feign.Response;
import java.lang.reflect.Type;

public class NoopTransformer implements Transformer {

  @Override
  public Object transformRequest(Object o, Type type, RequestTemplate requestTemplate) {
    return o;
  }

  @Override
  public Object transformResponse(Object o, Type type, Response response) {
    return o;
  }
}
