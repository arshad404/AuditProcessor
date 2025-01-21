package com.phonepe.payments.fundtransfer.codec;

import feign.RequestTemplate;
import feign.Response;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import java.io.IOException;
import java.lang.reflect.Type;

public class NoopTransformer implements Transformer {

  private final Decoder jacksonDecoder;

  public NoopTransformer(Decoder decoder) {
    this.jacksonDecoder = decoder;
  }

  public NoopTransformer() {
    jacksonDecoder = new JacksonDecoder();
  }

  @Override
  public Object transformRequest(Object o, Type type, RequestTemplate requestTemplate) {
    // This is a noop transformer which does nothing
    return o;
  }

  @Override
  public Object decodeResponse(Response response, Type type) throws IOException {
    // In Default, we are supporting Jackson Encoder and Decoder
    return jacksonDecoder.decode(response, type);
  }

}
