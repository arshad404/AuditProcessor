package com.phonepe.payments.fundtransfer.codec;

import static java.util.Objects.isNull;

import com.phonepe.payments.fundtransfer.exceptions.AuditEncoderException;
import com.phonepe.payments.fundtransfer.exceptions.AuditRequestContextException;
import com.phonepe.payments.fundtransfer.exceptions.DataStoreException;
import feign.FeignException;
import feign.Request.HttpMethod;
import feign.Response;
import feign.codec.Decoder;
import java.lang.reflect.Type;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AuditDecoder<A extends AuditContext> implements Decoder {

  private final AuditContextStore<A> auditContextStore;
  private final Transformer transformer;
  private final AuditDataStore<A> dataStore;

  protected AuditDecoder(AuditContextStore<A> auditContextStore, Transformer transformer,
      AuditDataStore<A> dataStore)
      throws AuditRequestContextException, DataStoreException {
    nullValidation(dataStore, auditContextStore);
    this.auditContextStore = auditContextStore;
    this.transformer = transformer != null ? transformer : new NoopTransformer();
    this.dataStore = dataStore;
  }

  @Override
  public Object decode(Response response, Type type) throws FeignException {
    try {
      Object decodedResponse = transformer.decodeResponse(response, type);
      if (isEligibleForAuditing(response)) {
        var auditRequestContext = auditContextStore.getAuditContext();
        auditContextStore.setAuditContext(decodedResponse, response, type);
        dataStore.saveAuditData(auditRequestContext);
      }
      return decodedResponse;
    } catch (Exception e) {
      throw new AuditEncoderException("error while decoding the audit", e);
    }
  }

  private void nullValidation(AuditDataStore<A> dataStore, AuditContextStore<A> auditContextStore)
      throws AuditRequestContextException, DataStoreException {
    if (isNull(auditContextStore)) {
      throw new AuditRequestContextException("AuditContextStore is not provided in AuditDecoder");
    }
    if (isNull(dataStore)) {
      throw new DataStoreException("AuditContextStore is not provided in AuditDecoder");
    }
  }

  private boolean isEligibleForAuditing(Response response) {
//    return false;
    return response.request().httpMethod() != HttpMethod.GET;
  }
}
