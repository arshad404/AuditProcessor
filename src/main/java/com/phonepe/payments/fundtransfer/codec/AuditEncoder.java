package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.exceptions.AuditEncoderException;
import com.phonepe.payments.fundtransfer.exceptions.AuditRequestContextException;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.jackson.JacksonEncoder;
import java.lang.reflect.Type;

public abstract class AuditEncoder<A extends AuditContext> implements Encoder {

  private final AuditContextStore<A> auditContextStore;
  private final Transformer transformer;
  private final Encoder encoder;
  private final ObjectMapper objectMapper;

  protected AuditEncoder(ObjectMapper objectMapper, AuditContextStore<A> auditContextStore,
      Transformer transformer, Encoder encoder) throws AuditRequestContextException {
    if(auditContextStore == null) {
      throw new AuditRequestContextException("AuditContextStore is not provided in AuditEncoder");
    }
    this.objectMapper = objectMapper != null ? objectMapper : new ObjectMapper();
    this.auditContextStore = auditContextStore;
    this.transformer = transformer != null ? transformer : new NoopTransformer();
    this.encoder = encoder != null ? encoder : new JacksonEncoder(this.objectMapper);
  }

  @Override
  public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
    try {
      auditContextStore.setAuditContext(object, bodyType, template);
      var transformedObject  = transformer.transformRequest(object, bodyType, template);
      //Get type of transformed object and then pass that to the delegated encoder
      var transformedType = this.objectMapper.getTypeFactory()
          .findClass(transformedObject.getClass().getName());
      encoder.encode(transformedObject, transformedType, template);
    } catch (AuditRequestContextException | ClassNotFoundException e) {
      throw new AuditEncoderException("exception while encoding while auditing", e);
    }
  }
}

