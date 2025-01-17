package com.phonepe.payments.fundtransfer.codec;


public interface AuditDataStore {
  void saveAuditData(Object auditRequestContext, Object auditResponse);
}
