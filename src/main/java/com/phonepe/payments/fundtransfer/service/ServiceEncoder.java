package com.phonepe.payments.fundtransfer.service;

import com.phonepe.payments.fundtransfer.codec.AuditEncoder;
import feign.RequestTemplate;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class ServiceEncoder extends AuditEncoder {

  @Override
  protected void sinkData(Object o, Type type, RequestTemplate requestTemplate, byte[] data) {
    System.out.println("Sinking data to the X database: " + new String(data, StandardCharsets.UTF_8));
  }
}
