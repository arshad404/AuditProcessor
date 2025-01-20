package com.phonepe.payments.fundtransfer.codec;


public interface IAuditDataStore {
  void saveAuditData(Object auditRequestContext, Object auditResponse);
}
