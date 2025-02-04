package com.phonepe.payments.fundtransfer.codec;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import lombok.Getter;

@Getter
public abstract class AuditErrorDecoder<A extends AuditContext> implements ErrorDecoder {

  private final RequestContextManager<A> requestContextManager;
  private final AuditDataStore<A> auditDataStore;

  protected AuditErrorDecoder(RequestContextManager<A> requestContextManager,
      AuditDataStore<A> auditDataStore) {
    this.requestContextManager = requestContextManager;
    this.auditDataStore = auditDataStore;
  }

  @Override
  public Exception decode(String methodKey, Response response) {
    // Extract response details for exception message
    int statusCode = response.status();
    String reason = response.reason();
    String responseBody = null;

    try {
      if (response.body() != null) {
        responseBody = Util.toString(response.body().asReader());
      }
    } catch (IOException e) {
      responseBody = "Failed to read response body";
    }

    // Construct and return a custom exception
    var apiException = new ApiException(
        methodKey,
        statusCode,
        reason,
        response.headers(),
        responseBody
    );

    var updatedContext = this.setAuditContext(methodKey, response, responseBody);
    this.requestContextManager.setContext(updatedContext);
    this.auditDataStore.saveAuditData(this.requestContextManager.getContext());

    return apiException;
  }

  protected abstract A setAuditContext(String methodKey, Response response, String errorBody);
}
