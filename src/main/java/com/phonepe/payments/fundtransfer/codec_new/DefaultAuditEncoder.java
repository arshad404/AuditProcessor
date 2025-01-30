package com.phonepe.payments.fundtransfer.codec_new;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestTemplate;
import feign.codec.Encoder;
import java.lang.reflect.Type;

public class DefaultAuditEncoder extends AuditEncoder<DefaultAuditContext> {

  public DefaultAuditEncoder(DefaultRequestContextManager defaultRequestContextManager,
      Encoder encoder, ObjectMapper objectMapper) {
    super(objectMapper, defaultRequestContextManager, encoder);
  }

  @Override
  protected DefaultAuditContext setAuditContext(Object object, Object transformedObject,
      Type bodyType, RequestTemplate template) {
    try {
      var context = this.getRequestContextManager().getContext();
      context.setRequestData(this.getObjectMapper().writeValueAsBytes(object));
      return context;
    } catch (JsonProcessingException e) {
      throw new CodecException("failed to set the audit context in default audit encoder", e);
    }
  }
}
