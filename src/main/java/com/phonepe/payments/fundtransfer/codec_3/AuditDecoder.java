package com.phonepe.payments.fundtransfer.codec_3;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import java.io.IOException;
import java.lang.reflect.Type;
import lombok.Builder;

public abstract class AuditDecoder<A extends AuditContext> implements Decoder {

  private final AuditContextStore<A> auditContextStore;
  private final ITransformer transformer;
  private final AuditDataStore dataStore;

  @Builder
  protected AuditDecoder(ObjectMapper objectMapper, AuditContextStore<A> auditContextStore, ITransformer transformer, AuditDataStore dataStore) {
    var mapper = objectMapper != null ? objectMapper : new ObjectMapper();
    this.auditContextStore = auditContextStore != null ? auditContextStore : createDefaultAuditContextStore(mapper);
    this.transformer = transformer != null ? transformer : new NoopTransformer();
    this.dataStore = dataStore != null ? dataStore : new NoopAuditDataStore();
  }

  @SuppressWarnings("unchecked")
  private AuditContextStore<A> createDefaultAuditContextStore(ObjectMapper objectMapper) {
    return (AuditContextStore<A>) new DefaultAuditContextStore(objectMapper);
  }

  @Override
  public Object decode(Response response, Type type) throws IOException, FeignException {
    var auditRequestContext = auditContextStore.getAuditContext();
    Object decodedResponse = transformer.decodeResponse(response, type);
    dataStore.saveAuditData(auditRequestContext, decodedResponse);
    return decodedResponse;
  }
}
