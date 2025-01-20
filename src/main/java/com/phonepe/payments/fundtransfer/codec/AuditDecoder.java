package com.phonepe.payments.fundtransfer.codec;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.phonepe.payments.fundtransfer.exceptions.AuditEncoderException;
import com.phonepe.payments.fundtransfer.exceptions.AuditRequestContextException;
import com.phonepe.payments.fundtransfer.exceptions.DataStoreException;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import java.lang.reflect.Type;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AuditDecoder<A extends IAuditContext> implements Decoder {

  private final IAuditContextStore<A> auditContextStore;
  private final ITransformer transformer;
  private final IAuditDataStore<A> dataStore;

  protected AuditDecoder(IAuditContextStore<A> auditContextStore, ITransformer transformer, IAuditDataStore<A> dataStore)
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
      if(nonNull(auditContextStore.getAuditContext())) {
        var auditRequestContext = auditContextStore.getAuditContext();
        auditContextStore.setAuditContext(decodedResponse, response, type);
        dataStore.saveAuditData(auditRequestContext);
      }
      return decodedResponse;
    } catch (Exception e) {
      throw new AuditEncoderException("error while decoding the audit", e);
    }
  }

  private void nullValidation(IAuditDataStore<A> dataStore, IAuditContextStore<A> auditContextStore)
      throws AuditRequestContextException, DataStoreException {
    if(auditContextStore == null) {
      throw new AuditRequestContextException("AuditContextStore is not provided in AuditDecoder");
    }
    if(dataStore == null) {
      throw new DataStoreException("AuditContextStore is not provided in AuditDecoder");
    }
  }
}
