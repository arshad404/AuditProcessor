package com.phonepe.payments.fundtransfer.codec_3;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultAuditDecoder extends AuditDecoder<DefaultAuditContext> {

  protected DefaultAuditDecoder(ObjectMapper objectMapper,
      AuditContextStore<DefaultAuditContext> auditContextStore, ITransformer transformer,
      AuditDataStore dataStore) {
    super(objectMapper, auditContextStore, transformer, dataStore);
  }
}
