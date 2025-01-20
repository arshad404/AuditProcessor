package com.phonepe.payments.fundtransfer.codec;


public interface IAuditDataStore<T extends IAuditContext> {
  void saveAuditData(T auditContext);
}
