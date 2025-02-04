package com.phonepe.payments.fundtransfer.codec;

import feign.Response;

public class DefaultAuditErrorDecoder extends AuditErrorDecoder<DefaultAuditContext> {

  protected DefaultAuditErrorDecoder(
      RequestContextManager<DefaultAuditContext> requestContextManager,
      AuditDataStore<DefaultAuditContext> auditDataStore) {
    super(requestContextManager, auditDataStore);
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
