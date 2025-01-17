package com.phonepe.payments.fundtransfer.codec_2;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Encoder;
import lombok.Builder;

public class DefaultAuditEncoder extends DefaultAuditContext<AuditContext> {

  @Builder
  public DefaultAuditEncoder(
      ObjectMapper objectMapper, ITransformer transformer, Encoder encoder) {
    super(new DefaultAuditContext(), objectMapper, new DefaultAuditContextStore(objectMapper), transformer, encoder);
  }
}
