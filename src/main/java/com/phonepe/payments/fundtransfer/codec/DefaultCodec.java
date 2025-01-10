package com.phonepe.payments.fundtransfer.codec;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class DefaultCodec {
  private static Encoder encoder;
  private static Decoder decoder;

  private DefaultCodec() {
    throw new IllegalStateException("Utility class");
  }

  public static Encoder getEncoder() {
    if (encoder == null) {
      synchronized (DefaultCodec.class) {
        if (encoder == null) {
          encoder = new JacksonEncoder();
        }
      }
    }
    return encoder;
  }

  public static Decoder getDecoder() {
    if (decoder == null) {
      synchronized (DefaultCodec.class) {
        if (decoder == null) {
          decoder = new JacksonDecoder();
        }
      }
    }
    return decoder;
  }
}