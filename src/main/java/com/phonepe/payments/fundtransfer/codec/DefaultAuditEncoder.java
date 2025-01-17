package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.service.TestAuditContextStore;
import feign.codec.Encoder;
import feign.jackson.JacksonEncoder;
import lombok.Builder;

public class DefaultAuditEncoder extends AuditEncoder<DefaultAuditContext> {

  @Builder
  public DefaultAuditEncoder(ObjectMapper objectMapper,
      TestAuditContextStore auditContextStore, NoopTransformer transformer,
      Encoder encoder) throws AuditRequestContextException {
    super(objectMapper, auditContextStore, transformer, new JacksonEncoder());
  }
}
