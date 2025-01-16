package com.phonepe.payments.fundtransfer.codec_2;

public interface AuditDataStore<U, V> {
  void saveAuditData(U auditRequestContext, V auditResponse);
}
