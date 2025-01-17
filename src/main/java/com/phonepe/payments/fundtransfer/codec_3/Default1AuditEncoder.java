package com.phonepe.payments.fundtransfer.codec_3;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Encoder;

public class Default1AuditEncoder extends AuditEncoder<Default1AuditContext> {

  protected Default1AuditEncoder(ObjectMapper objectMapper,
      AuditContextStore<Default1AuditContext> auditContextStore, ITransformer transformer,
      Encoder encoder) {
    super(objectMapper, auditContextStore, transformer, encoder);
  }
}
