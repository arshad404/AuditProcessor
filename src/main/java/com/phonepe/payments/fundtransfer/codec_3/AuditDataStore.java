package com.phonepe.payments.fundtransfer.codec_3;


public interface AuditDataStore {
  void saveAuditData(Object auditRequestContext, Object auditResponse);
}
