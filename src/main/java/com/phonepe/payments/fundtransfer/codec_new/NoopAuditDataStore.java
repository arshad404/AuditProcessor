package com.phonepe.payments.fundtransfer.codec_new;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoopAuditDataStore implements AuditDataStore<DefaultAuditContext> {

  @Override
  public void saveAuditData(DefaultAuditContext auditContext) {
    log.info("Logging data using NoopAuditDataStore, AuditRequest context: {}",
        auditContext.toString());
  }
}
