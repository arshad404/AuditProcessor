package com.phonepe.payments.fundtransfer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.codec.Decoder;
import com.phonepe.payments.fundtransfer.compression.Compression;
import com.phonepe.payments.fundtransfer.database.AuditData;
import com.phonepe.payments.fundtransfer.model.AuditProcessorConfig;
import feign.Response;
import feign.jackson.JacksonDecoder;
import java.io.IOException;
import java.lang.reflect.Type;

public class ServiceDecoder extends Decoder {

  JacksonDecoder jacksonDecoder = new JacksonDecoder();
  ObjectMapper objectMapper = new ObjectMapper();

  public ServiceDecoder(Compression compression,
      AuditData auditData,
      AuditProcessorConfig auditProcessorConfig) {
    super( compression, auditData, auditProcessorConfig);
  }

  @Override
  protected byte[] extract(Object response, Type type)  {
    // or if(type == User.class)
    if(response instanceof User user) {
      System.out.println("User: " + user.getName() + " " + user.getAge());
      try {
        return objectMapper.writeValueAsBytes(user);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
    return new byte[0];
  }

  @Override
  protected Object transform(Response response, Type type) {
    try {
      return jacksonDecoder.decode(response, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
