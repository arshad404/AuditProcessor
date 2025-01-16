package com.phonepe.payments.fundtransfer.codec_2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.codec_1.AuditRequestContextException;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.jackson.JacksonEncoder;
import java.lang.reflect.Type;

public class AuditEncoder implements Encoder {

  private final ObjectMapper objectMapper;
  private final AuditContextStore auditContextStore;
  private final ITransformer transformer;
  private final Encoder encoder;

  // Private constructor, used by the Builder
  private AuditEncoder(Builder builder) {
    this.objectMapper = builder.objectMapper != null ? builder.objectMapper : new ObjectMapper();
    this.auditContextStore = builder.auditContextStore != null ? builder.auditContextStore : new DefaultAuditContextStore(this.objectMapper);
    this.transformer = builder.transformer != null ? builder.transformer : new NoopTransformer();
    this.encoder = builder.encoder != null ? builder.encoder : new JacksonEncoder();
  }

  @Override
  public void encode(Object object, Type bodyType, RequestTemplate template)
      throws EncodeException {
    try {
      auditContextStore.setAuditContext(object, bodyType, template);
      transformer.transformRequest(object, bodyType, template);
      encoder.encode(object, bodyType, template);
    } catch (AuditRequestContextException e) {
      throw new EncodeException(e.getMessage(), e);
    }
  }


  public static class Builder {
    private ObjectMapper objectMapper;
    private AuditContextStore auditContextStore;
    private ITransformer transformer;
    private Encoder encoder;

    // Setters for each field
    public Builder setObjectMapper(ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
      return this;
    }

    public Builder setAuditContextStore(AuditContextStore auditContextStore) {
      this.auditContextStore = auditContextStore;
      return this;
    }

    public Builder setTransformer(ITransformer transformer) {
      this.transformer = transformer;
      return this;
    }

    public Builder setEncoder(Encoder encoder) {
      this.encoder = encoder;
      return this;
    }

    // Build the final AuditEncoder object
    public AuditEncoder build() {
      return new AuditEncoder(this);
    }
  }
}

