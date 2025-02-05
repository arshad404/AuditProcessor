package com.phonepe.payments.fundtransfer.codec;

import static java.util.Objects.isNull;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import lombok.Getter;

@Getter
public abstract class AuditErrorDecoder<A extends AuditContext> implements ErrorDecoder {

  private final RequestContextManager<A> requestContextManager;
  private final AuditDataStore<A> auditDataStore;
  private final ErrorDecoder delegate;

  protected AuditErrorDecoder(RequestContextManager<A> requestContextManager,
      AuditDataStore<A> auditDataStore, ErrorDecoder delegate) {
    this.requestContextManager = requestContextManager;
    this.auditDataStore = auditDataStore;
    this.delegate = delegate;
  }

  @Override
  public Exception decode(String methodKey, Response response) {
    // Extract response details for exception message
    String responseBody;
    if (isNull(response.body()) || response.body().length() == 0) {
      responseBody = "EMPTY";
    } else {
      try {
        responseBody = new String(Util.toByteArray(response.body().asInputStream()));
      } catch (IOException e) {
        responseBody = "Failed to read response body";
      }
    }
    var updatedContext = this.setAuditContext(methodKey, response, responseBody);
    this.requestContextManager.setContext(updatedContext);
    this.auditDataStore.saveAuditData(this.requestContextManager.getContext());
    return delegate.decode(methodKey, response);
  }

  protected abstract A setAuditContext(String methodKey, Response response, String errorBody);
}
