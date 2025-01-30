package com.phonepe.payments.fundtransfer.codec;

public class TestAuditRequestInterceptor extends AuditRequestInterceptor<DefaultAuditContext> {

  public TestAuditRequestInterceptor(AuditContextStore<DefaultAuditContext> auditContextStore) {
    super(auditContextStore);
  }
}
