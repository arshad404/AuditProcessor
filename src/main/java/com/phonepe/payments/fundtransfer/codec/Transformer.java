package com.phonepe.payments.fundtransfer.codec;

import feign.RequestTemplate;
import feign.Response;
import java.lang.reflect.Type;

public interface Transformer {

  String value();

  // Return the Transformed Object
  Object transformRequest(Object o, Type type, RequestTemplate requestTemplate);

  Object transformResponse(Object o, Type type, Response response);

  Response transformResponseInLogger(Response response);
}
