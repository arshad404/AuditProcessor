package com.phonepe.payments.fundtransfer.servicetest;

import com.phonepe.payments.fundtransfer.codec.AuditContextStore;
import com.phonepe.payments.fundtransfer.codec.AuditDataStore;
import com.phonepe.payments.fundtransfer.codec.AuditDecoder;
import com.phonepe.payments.fundtransfer.codec.DefaultAuditContext;
import com.phonepe.payments.fundtransfer.codec.Transformer;
import com.phonepe.payments.fundtransfer.exceptions.AuditRequestContextException;
import com.phonepe.payments.fundtransfer.exceptions.DataStoreException;
import lombok.Builder;

public class ServiceAuditDecoder  extends AuditDecoder<DefaultAuditContext> {

  @Builder
  protected ServiceAuditDecoder(AuditContextStore<DefaultAuditContext> auditContextStore,
      Transformer transformer,
      AuditDataStore<DefaultAuditContext> dataStore)
      throws AuditRequestContextException, DataStoreException {
    super(auditContextStore, transformer, dataStore);
  }
}
