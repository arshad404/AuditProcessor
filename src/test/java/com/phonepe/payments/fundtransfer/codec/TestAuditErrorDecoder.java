package com.phonepe.payments.fundtransfer.codec;

public class TestAuditErrorDecoder extends AuditErrorDecoder<DefaultAuditContext> {

  public TestAuditErrorDecoder(TestAuditContextStore auditContextStore,
      TestAuditDataStore dataStore) {
    super(auditContextStore, dataStore);
  }
}
