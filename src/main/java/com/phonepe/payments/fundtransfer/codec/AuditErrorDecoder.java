package com.phonepe.payments.fundtransfer.codec;

import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AuditErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    String responseBody = null;
    try {
      responseBody = getResponseBody(response);
      String message = String.format("Method: %s, Status: %d, Body: %s", methodKey, response.status(), responseBody);
      if (response.status() >= 400 && response.status() < 500) {
        return new FourXXErrorDecoderException(message);
      } else if (response.status() >= 500) {
        return new FiveXXErrorDecoderException(message);
      } else {
        return new Exception(message);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String getResponseBody(Response response) throws IOException {
    if (response.body() == null) {
      return null;
    }

    // try-with-resources to ensure the scanner is closed automatically
    try (Scanner scanner = new Scanner(response.body().asInputStream(), StandardCharsets.UTF_8)) {
      // delimiter "\\A" to read the entire body as a single string
      return scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
    }
  }
}
