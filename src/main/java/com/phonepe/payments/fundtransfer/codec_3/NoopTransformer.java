package com.phonepe.payments.fundtransfer.codec_3;

import feign.RequestTemplate;
import feign.Response;
import feign.jackson.JacksonDecoder;
import java.io.IOException;
import java.lang.reflect.Type;

public class NoopTransformer implements ITransformer {

  private final JacksonDecoder jacksonDecoder;

  public NoopTransformer() {
    jacksonDecoder = new JacksonDecoder();
  }

  @Override
  public void transformRequest(Object o, Type type, RequestTemplate requestTemplate) {
    // This is a noop transformer which does nothing
  }

  @Override
  public Object decodeResponse(Response response, Type type) throws IOException {
    // In Default, we are supporting Jackson Encoder and Decoder
    return jacksonDecoder.decode(response, type);
  }

}
