package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.exceptions.AuditRequestContextException;
import feign.jackson.JacksonEncoder;
import lombok.Builder;

public class TestAuditEncoder extends AuditEncoder<DefaultAuditContext> {

  @Builder
  public TestAuditEncoder(ObjectMapper objectMapper,
      TestAuditContextStore auditContextStore, NoopTransformer transformer) throws AuditRequestContextException {
    super(objectMapper, auditContextStore, transformer, new JacksonEncoder());
  }
}
