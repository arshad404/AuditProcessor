package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.exceptions.AuditEncoderException;
import com.phonepe.payments.fundtransfer.exceptions.AuditRequestContextException;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.jackson.JacksonEncoder;
import java.lang.reflect.Type;

public abstract class AuditEncoder<A extends IAuditContext> implements Encoder {

  private final IAuditContextStore<A> auditContextStore;
  private final ITransformer transformer;
  private final Encoder encoder;

  protected AuditEncoder(ObjectMapper objectMapper, IAuditContextStore<A> auditContextStore,
      ITransformer transformer, Encoder encoder) throws AuditRequestContextException {
    if(auditContextStore == null) {
      throw new AuditRequestContextException("AuditContextStore is not provided in AuditEncoder");
    }
    ObjectMapper mapper = objectMapper != null ? objectMapper : new ObjectMapper();
    this.auditContextStore = auditContextStore;
    this.transformer = transformer != null ? transformer : new NoopTransformer();
    this.encoder = encoder != null ? encoder : new JacksonEncoder(mapper);
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

