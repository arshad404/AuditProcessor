package com.phonepe.payments.fundtransfer.codec_3;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.jackson.JacksonEncoder;
import java.lang.reflect.Type;
import lombok.Builder;

public abstract class AuditEncoder<A extends AuditContext> implements Encoder {

  private final AuditContextStore<A> auditContextStore;
  private final ITransformer transformer;
  private final Encoder encoder;

  @Builder
  protected AuditEncoder(ObjectMapper objectMapper, AuditContextStore<A> auditContextStore,
      ITransformer transformer, Encoder encoder) {
    ObjectMapper mapper = objectMapper != null ? objectMapper : new ObjectMapper();
    this.auditContextStore = auditContextStore;
    this.transformer = transformer != null ? transformer : createNoopTransformer();
    this.encoder = encoder != null ? encoder : new JacksonEncoder(mapper);
  }

  private ITransformer createNoopTransformer() {
    return new NoopTransformer();
  }

  @Override
  public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
    try {
      auditContextStore.setAuditContext(object, bodyType, template);
      transformer.transformRequest(object, bodyType, template);
      encoder.encode(object, bodyType, template);
    } catch (AuditRequestContextException e) {
      throw new AuditEncoderException("exception while encoding while auditing", e);
    }
  }
}

