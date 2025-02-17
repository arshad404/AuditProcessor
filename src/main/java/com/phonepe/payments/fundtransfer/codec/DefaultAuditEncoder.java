package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestTemplate;
import feign.codec.Encoder;
import java.lang.reflect.Type;

public class DefaultAuditEncoder extends AuditEncoder<DefaultAuditContext> {

  public DefaultAuditEncoder(Encoder encoder, ObjectMapper objectMapper, Class<?> client) {
    super(objectMapper, DefaultRequestContextManager.getInstance(), encoder, client);
  }

  @Override
  protected DefaultAuditContext setAuditContext(Object object, Type bodyType,
      RequestTemplate template) {
    try {
      var context = this.getRequestContextManager().getContext();
      context.setRequestData(this.getObjectMapper().writeValueAsBytes(object));
      return context;
    } catch (JsonProcessingException e) {
      throw new CodecException("failed to set the audit context in default audit encoder", e);
    }
  }
}
