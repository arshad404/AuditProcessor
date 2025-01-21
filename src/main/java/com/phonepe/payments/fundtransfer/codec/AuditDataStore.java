package com.phonepe.payments.fundtransfer.codec;


public interface AuditDataStore<T extends AuditContext> {
  void saveAuditData(T auditContext);
}
