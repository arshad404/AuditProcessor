package com.phonepe.payments.fundtransfer.codec;

import feign.RequestTemplate;
import feign.Response;
import java.io.IOException;
import java.lang.reflect.Type;

public interface Transformer {
  // Before sending request to the server, any transformation that a user want to do
  Object transformRequest(Object o, Type type, RequestTemplate requestTemplate);
  Object decodeResponse(Response response, Type type) throws IOException;
}
