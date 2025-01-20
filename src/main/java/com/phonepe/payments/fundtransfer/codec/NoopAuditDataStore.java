package com.phonepe.payments.fundtransfer.codec;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoopAuditDataStore implements IAuditDataStore {

  @Override
  public void saveAuditData(Object auditRequestContext, Object auditResponse) {
    log.info("Logging data using NoopAuditDataStore, AuditRequest context: {}, Audit Response: {}", auditRequestContext.toString(), auditResponse.toString());
  }
}
