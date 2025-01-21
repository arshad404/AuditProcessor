package com.phonepe.payments.fundtransfer.servicetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.codec.AuditContextStore;
import com.phonepe.payments.fundtransfer.codec.AuditEncoder;
import com.phonepe.payments.fundtransfer.codec.DefaultAuditContext;
import com.phonepe.payments.fundtransfer.codec.Transformer;
import com.phonepe.payments.fundtransfer.exceptions.AuditRequestContextException;
import feign.codec.Encoder;
import lombok.Builder;

public class ServiceAuditEncoder extends AuditEncoder<DefaultAuditContext>  {

  @Builder
  protected ServiceAuditEncoder(ObjectMapper objectMapper,
      AuditContextStore<DefaultAuditContext> auditContextStore,
      Transformer transformer, Encoder encoder)
      throws AuditRequestContextException {
    super(objectMapper, auditContextStore, transformer, encoder);
  }
}
