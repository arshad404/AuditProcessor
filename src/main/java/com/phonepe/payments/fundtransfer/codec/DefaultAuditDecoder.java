package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.Decoder;
import java.lang.reflect.Type;

public class DefaultAuditDecoder extends AuditDecoder<DefaultAuditContext> {

  public DefaultAuditDecoder(Decoder delegate, AuditDataStore<DefaultAuditContext> auditDataStore,
      ObjectMapper objectMapper, Class<?> client) {
    super(DefaultRequestContextManager.getInstance(), delegate, client, objectMapper,
        auditDataStore);
  }

  @Override
  protected DefaultAuditContext setAuditContext(Response response, Type type,
      Object decodedObject) {
    try {
      var context = this.getRequestContextManager().getContext();
      context.setResponseData(this.getObjectMapper().writeValueAsBytes(decodedObject));
      return context;
    } catch (JsonProcessingException e) {
      throw new CodecException("failed to set the audit context in default audit encoder", e);
    }
  }
}
