package com.phonepe.payments.fundtransfer.servicetest;

import com.phonepe.payments.fundtransfer.codec.AuditDataStore;
import com.phonepe.payments.fundtransfer.codec.DefaultAuditContext;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ServiceAuditDataStore implements AuditDataStore<DefaultAuditContext> {

  @Override
  public void saveAuditData(DefaultAuditContext auditContext) {
    log.info("Logging data using NoopAuditDataStore, AuditRequest context: {}", auditContext);
  }

}