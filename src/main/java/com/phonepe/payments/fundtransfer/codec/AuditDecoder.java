package com.phonepe.payments.fundtransfer.codec;

import feign.FeignException;
import feign.Request.HttpMethod;
import feign.Response;
import feign.codec.Decoder;
import java.lang.reflect.Type;

public abstract class AuditDecoder<A extends AuditContext> implements Decoder {

  private final AuditContextStore<A> auditContextStore;
  private final ITransformer transformer;
  private final AuditDataStore dataStore;

  protected AuditDecoder(AuditContextStore<A> auditContextStore, ITransformer transformer, AuditDataStore dataStore)
      throws AuditRequestContextException {
    if(auditContextStore == null) {
      throw new AuditRequestContextException("AuditContextStore is not provided in AuditDecoder");
    }
    this.auditContextStore = auditContextStore;
    this.transformer = transformer != null ? transformer : new NoopTransformer();
    this.dataStore = dataStore != null ? dataStore : new NoopAuditDataStore();
  }

  @Override
  public Object decode(Response response, Type type) throws FeignException {
    try {
      Object decodedResponse = transformer.decodeResponse(response, type);
      if(response.request().httpMethod() == HttpMethod.POST) {
        var auditRequestContext = auditContextStore.getAuditContext();
        dataStore.saveAuditData(auditRequestContext, decodedResponse);
      }
      return decodedResponse;
    } catch (Exception e) {
      throw new AuditEncoderException("error while decoding the audit", e);
    }
  }
}
