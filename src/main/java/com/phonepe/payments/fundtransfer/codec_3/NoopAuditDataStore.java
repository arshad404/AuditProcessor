package com.phonepe.payments.fundtransfer.codec_3;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoopAuditDataStore implements AuditDataStore {

  @Override
  public void saveAuditData(Object auditRequestContext, Object auditResponse) {
    log.info("No-op: Skipping saving audit data. Request context: {}", auditRequestContext);
  }
}
