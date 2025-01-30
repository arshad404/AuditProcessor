package com.phonepe.payments.fundtransfer.codec_new;

import feign.Response;

public class DefaultAuditResponseLogger extends AuditResponseLogger<DefaultAuditContext> {

  public DefaultAuditResponseLogger(DefaultRequestContextManager requestContextManager,
      NoopAuditDataStore auditDataStore) {
    super(requestContextManager, auditDataStore);
  }

  @Override
  protected void updateAuditContext(DefaultAuditContext auditContext, Response response) {
    auditContext.setStatusCode(response.status());
    auditContext.setReason(response.reason());
  }
}
