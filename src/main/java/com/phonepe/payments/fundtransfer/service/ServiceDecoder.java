package com.phonepe.payments.fundtransfer.service;

import com.phonepe.payments.fundtransfer.codec.AuditDecoder;
import com.phonepe.payments.fundtransfer.codec.DefaultCodec;
import feign.Response;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class ServiceDecoder extends AuditDecoder {

  @Override
  protected void sinkData(Response response, Type type, byte[] data) {
    System.out.println("Sinking data to the X database: " + new String(data, StandardCharsets.UTF_8));
  }

  @Override
  protected byte[] compress(byte[] data) {
    System.out.println("Here we are");
    return data;
  }
}
