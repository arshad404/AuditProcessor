package com.phonepe.payments.fundtransfer.codec;

import com.phonepe.payments.fundtransfer.exceptions.AuditRequestContextException;
import com.phonepe.payments.fundtransfer.exceptions.FiveXXErrorDecoderException;
import com.phonepe.payments.fundtransfer.exceptions.FourXXErrorDecoderException;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AuditErrorDecoder<A extends IAuditContext> implements ErrorDecoder {

  private final IAuditContextStore<A> auditContextStore;
  private final IAuditDataStore<A> dataStore;

  public AuditErrorDecoder(IAuditContextStore<A> auditContextStore, IAuditDataStore<A> dataStore) {
    this.auditContextStore = auditContextStore;
    this.dataStore = dataStore;
  }

  @Override
  public Exception decode(String methodKey, Response response) {
    try {
      // Preparing response body
      String responseBody = getResponseBody(response);
      String message = String.format("Method: %s, Status: %d, Body: %s", methodKey, response.status(), responseBody);
      // Audit context for setting the error & Save Audit
      var auditRequestContext = auditContextStore.getAuditContext();
      auditContextStore.setAuditContext(auditRequestContext, response, String.class);
      dataStore.saveAuditData(auditRequestContext);
      if (response.status() >= 400 && response.status() < 500) {
        return new FourXXErrorDecoderException(message);
      } else if (response.status() >= 500) {
        return new FiveXXErrorDecoderException(message);
      } else {
        return new Exception(message);
      }
    } catch (IOException | AuditRequestContextException e) {
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
