package com.phonepe.payments.fundtransfer.codec;

import feign.Response;
import feign.codec.ErrorDecoder;

public class DefaultAuditErrorDecoder extends AuditErrorDecoder<DefaultAuditContext> {

  protected DefaultAuditErrorDecoder(
      AuditDataStore<DefaultAuditContext> auditDataStore) {
    super(DefaultRequestContextManager.getInstance(), auditDataStore, new ErrorDecoder.Default());
  }

  @Override
  protected DefaultAuditContext setAuditContext(String methodKey, Response response,
      String errorBody) {
    var context = this.getRequestContextManager().getContext();
    context.setErrorBody(errorBody);
    context.setStatusCode(response.status());
    context.setReason(response.reason());
    return context;
  }
}
