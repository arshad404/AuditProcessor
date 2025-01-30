package com.phonepe.payments.fundtransfer.codec;

import com.phonepe.payments.fundtransfer.exceptions.AuditRequestContextException;
import com.phonepe.payments.fundtransfer.exceptions.DataStoreException;
import lombok.Builder;


public class TestAuditDecoder extends AuditDecoder<DefaultAuditContext> {

  @Builder
  public TestAuditDecoder(TestAuditContextStore auditContextStore, NoopTransformer transformer,
      TestAuditDataStore dataStore) throws AuditRequestContextException, DataStoreException {
    super(auditContextStore, transformer, dataStore);
  }
}