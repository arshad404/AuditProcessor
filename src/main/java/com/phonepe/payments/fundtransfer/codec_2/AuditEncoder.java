package com.phonepe.payments.fundtransfer.codec_2;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.jackson.JacksonEncoder;
import java.lang.reflect.Type;
import lombok.Builder;

public abstract class AuditEncoder<A extends AuditContext> implements Encoder {

  private final A auditContext;
  private final ObjectMapper objectMapper;
  private final AuditContextStore<A> auditContextStore;
  private final ITransformer transformer;
  private final Encoder encoder;

  @Builder
  protected AuditEncoder(AuditContext auditContext, ObjectMapper objectMapper, AuditContextStore<A> auditContextStore,
      ITransformer transformer, Encoder encoder) {
    this.auditContext = auditContext != null ? (A) auditContext : (A) new DefaultAuditContext();
    this.objectMapper = objectMapper != null ? objectMapper : new ObjectMapper();
    this.auditContextStore = auditContextStore;
    this.transformer = transformer != null ? transformer : new NoopTransformer();
    this.encoder = encoder != null ? encoder : new JacksonEncoder(this.objectMapper);
  }

  @Override
  public void encode(Object object, Type bodyType, RequestTemplate template)
      throws EncodeException {
    try {
      auditContextStore.setAuditContext(object, bodyType, template);
      transformer.transformRequest(object, bodyType, template);
      encoder.encode(object, bodyType, template);
    } catch (com.phonepe.payments.fundtransfer.codec_2.AuditRequestContextException e) {
      throw new RuntimeException(e);
    }
  }
}

