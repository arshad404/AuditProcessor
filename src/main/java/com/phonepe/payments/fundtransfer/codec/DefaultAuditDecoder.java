package com.phonepe.payments.fundtransfer.codec;

import com.phonepe.payments.fundtransfer.exceptions.AuditRequestContextException;
import com.phonepe.payments.fundtransfer.exceptions.DataStoreException;
import lombok.Builder;


public class DefaultAuditDecoder extends AuditDecoder<DefaultAuditContext> {

  @Builder
  public DefaultAuditDecoder(AuditContextStore<DefaultAuditContext> auditContextStore, NoopTransformer transformer,
      NoopAuditDataStore dataStore) throws AuditRequestContextException, DataStoreException {
    super(auditContextStore, transformer, dataStore);
  }
}
