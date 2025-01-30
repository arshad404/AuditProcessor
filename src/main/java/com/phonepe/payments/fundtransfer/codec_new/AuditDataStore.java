package com.phonepe.payments.fundtransfer.codec_new;

public interface AuditDataStore<T extends AuditContext> {

  void saveAuditData(T auditContext);
}
