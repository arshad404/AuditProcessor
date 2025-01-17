package com.phonepe.payments.fundtransfer.codec;

import com.phonepe.payments.fundtransfer.service.TestAuditContextStore;
import lombok.Builder;


public class DefaultAuditDecoder extends AuditDecoder<DefaultAuditContext> {

  @Builder
  public DefaultAuditDecoder(TestAuditContextStore auditContextStore, NoopTransformer transformer,
      NoopAuditDataStore dataStore) throws AuditRequestContextException {
    super(auditContextStore, transformer, dataStore);
  }
}
