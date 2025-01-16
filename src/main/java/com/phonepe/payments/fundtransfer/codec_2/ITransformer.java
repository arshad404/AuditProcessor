package com.phonepe.payments.fundtransfer.codec_2;

import feign.RequestTemplate;
import feign.Response;
import java.io.IOException;
import java.lang.reflect.Type;

public interface ITransformer {
  // Before sending request to the server, any transformation that a user want to do
  void transformRequest(Object o, Type type, RequestTemplate requestTemplate);
  Object decodeResponse(Response response, Type type) throws IOException;
}
