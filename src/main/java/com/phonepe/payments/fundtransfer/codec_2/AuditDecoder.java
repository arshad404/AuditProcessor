package com.phonepe.payments.fundtransfer.codec_2;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import java.io.IOException;
import java.lang.reflect.Type;

public class AuditDecoder implements Decoder {

  private final ObjectMapper objectMapper;
  private final AuditContextStore auditContextStore;
  private final ITransformer transformer;
  private final AuditDataStore dataStore;

  // Private constructor, used by the Builder
  private AuditDecoder(Builder builder) {
    this.objectMapper = builder.objectMapper != null ? builder.objectMapper : new ObjectMapper();
    this.auditContextStore = builder.auditContextStore != null ? builder.auditContextStore : new DefaultAuditContextStore(this.objectMapper);
    this.transformer = builder.transformer != null ? builder.transformer : new NoopTransformer();
    this.dataStore = builder.dataStore != null ? builder.dataStore : new NoopAuditDataStore();
  }

  @Override
  public Object decode(Response response, Type type)
      throws IOException, FeignException {
    // GET REQUEST DATA
    var auditRequestContext = auditContextStore.getAuditContext();
    Object decodedResponse = transformer.decodeResponse(response, type);
    dataStore.saveAuditData(auditRequestContext, decodedResponse);
    return decodedResponse;
  }

  public static class Builder {
    private ObjectMapper objectMapper;
    private AuditContextStore auditContextStore;
    private ITransformer transformer;
    private AuditDataStore dataStore;

    // Setters for each field
    public AuditDecoder.Builder setObjectMapper(ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
      return this;
    }

    public AuditDecoder.Builder setAuditContextStore(AuditContextStore auditContextStore) {
      this.auditContextStore = auditContextStore;
      return this;
    }

    public AuditDecoder.Builder setTransformer(ITransformer transformer) {
      this.transformer = transformer;
      return this;
    }

    public AuditDecoder.Builder setAuditDataStore(AuditDataStore auditDataStore) {
      this.dataStore = auditDataStore;
      return this;
    }

    // Build the final AuditEncoder object
    public AuditDecoder build() {
      return new AuditDecoder(this);
    }
  }
}
