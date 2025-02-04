package com.phonepe.payments.fundtransfer.codec;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class TestAuditDataStore implements AuditDataStore<DefaultAuditContext> {

  public final Map<String, DefaultAuditContext> auditDataMap = new HashMap<>();

  @Override
  public void saveAuditData(DefaultAuditContext auditContext) {
    log.info("Logging data using NoopAuditDataStore, AuditRequest context: {}",
        auditContext.toString());
    auditDataMap.put(auditContext.getId(), auditContext);
  }

}