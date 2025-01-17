package com.phonepe.payments.fundtransfer.codec_3;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Encoder;

public class DefaultAuditEncoder extends AuditEncoder<DefaultAuditContext> {

  protected DefaultAuditEncoder(ObjectMapper objectMapper,
      AuditContextStore<DefaultAuditContext> auditContextStore, ITransformer transformer,
      Encoder encoder) {
    super(objectMapper, auditContextStore, transformer, encoder);
  }
}
