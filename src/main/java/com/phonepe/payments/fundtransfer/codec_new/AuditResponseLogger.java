package com.phonepe.payments.fundtransfer.codec_new;

import feign.Logger;
import feign.Request;
import feign.Response;
import java.io.IOException;

public abstract class AuditResponseLogger<T extends AuditContext> extends Logger {

  private final RequestContextManager<T> requestContextManager;
  private final AuditDataStore<T> auditDataStore;

  public AuditResponseLogger(RequestContextManager<T> requestContextManager,
      AuditDataStore<T> auditDataStore) {
    this.requestContextManager = requestContextManager;
    this.auditDataStore = auditDataStore;
  }

  @Override
  protected void log(String configKey, String format, Object... args) {
    // Implementing the spicific methods
    System.out.println();
  }

  @Override
  protected void logRequest(String configKey, Level logLevel, Request request) {
    super.logRequest(configKey, logLevel, request);
  }

  @Override
  protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response,
      long elapsedTime) throws IOException {

    var context = requestContextManager.getContext();
    this.updateAuditContext(context, response);
    this.requestContextManager.setContext(context);

    // save the data in the response
    auditDataStore.saveAuditData(this.requestContextManager.getContext());

    return super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);
  }

  protected abstract void updateAuditContext(T auditContext, Response response);
}
