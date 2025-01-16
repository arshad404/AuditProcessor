package com.phonepe.payments.fundtransfer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class SerializationUtil {

  static final ObjectMapper objectMapper = new ObjectMapper();

  public static byte[] toByteArray(Object object) throws IOException {
    // Convert the object to a byte[]
    return objectMapper.writeValueAsBytes(object);
  }
}