package com.phonepe.payments.fundtransfer.servicetest;

import com.phonepe.payments.fundtransfer.codec.Transformer;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import java.io.IOException;
import java.lang.reflect.Type;

public class ServiceTransformer implements Transformer {

  Decoder jacksonDecoder = new JacksonDecoder();



  @Override
  public Object transformRequest(Object o, Type type, RequestTemplate requestTemplate) {
    User user = (User) o;
    return new UserChange(user.getName());
  }

  @Override
  public Object decodeResponse(Response response, Type type) throws IOException {
    Object decoded = jacksonDecoder.decode(response, type);
    return decoded;
  }
}
