package com.phonepe.payments.fundtransfer.codec_1;

import feign.RequestTemplate;
import feign.Response;
import feign.jackson.JacksonDecoder;
import java.io.IOException;
import java.lang.reflect.Type;

public class TransformServiceNoopImpl implements TransformService {

  JacksonDecoder jacksonDecoder;

  TransformServiceNoopImpl(JacksonDecoder jacksonDecoder) {
    this.jacksonDecoder = new JacksonDecoder();
  }

  @Override
  public void transformRequest(Object o, Type type, RequestTemplate requestTemplate) {
    // This is a noop transformer which does nothing
  }

  @Override
  public Object decodeResponse(Response response, Type type) throws IOException {
    // This is a noop transformer which does nothing
    return jacksonDecoder.decode(response, type);
  }
}
