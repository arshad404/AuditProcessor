package com.phonepe.payments.fundtransfer.codec;

import static java.util.Objects.nonNull;

import feign.Logger;
import feign.Response;
import java.io.IOException;

public abstract class AuditResponseLogger<T extends AuditContext> extends Logger {

  private final RequestContextManager<T> requestContextManager;
  private final AuditDataStore<T> auditDataStore;
  private final TransformerManager transformerManager;

  protected AuditResponseLogger(RequestContextManager<T> requestContextManager,
      AuditDataStore<T> auditDataStore, Class<?> client) {
    this.requestContextManager = requestContextManager;
    this.auditDataStore = auditDataStore;
    this.transformerManager = new TransformerManager(client);
  }

  @Override
  protected void log(String configKey, String format, Object... args) {
    // Implementing the specific methods
  }


  @Override
  protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response,
      long elapsedTime) throws IOException {

    Boolean isDecoderSkipped = Utils.isDecoderSkipped(configKey) && response.status() < 400;

    if (Boolean.TRUE.equals(isDecoderSkipped)) {

      // Retrieve transformer name
      String transformerName = Utils.getTransformerName(response.request().requestTemplate());
      if (nonNull(transformerName)) {
        response = transformerManager.applyLoggerTransformation(transformerName, response);
      }
    }

    var context = requestContextManager.getContext();
    this.updateAuditContext(context, response, isDecoderSkipped);
    this.requestContextManager.setContext(context);

    if (Boolean.TRUE.equals(isDecoderSkipped)) {
      auditDataStore.saveAuditData(this.requestContextManager.getContext());
    }

    return super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);
  }

  protected abstract void updateAuditContext(T auditContext, Response response,
      Boolean isDecoderSkipped);
}
