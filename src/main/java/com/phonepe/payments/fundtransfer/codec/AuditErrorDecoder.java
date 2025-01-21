package com.phonepe.payments.fundtransfer.codec;

import com.phonepe.payments.fundtransfer.exceptions.AuditRequestContextException;
import com.phonepe.payments.fundtransfer.exceptions.FiveXXErrorDecoderException;
import com.phonepe.payments.fundtransfer.exceptions.FourXXErrorDecoderException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class AuditErrorDecoder<A extends AuditContext> implements ErrorDecoder {

  private final AuditContextStore<A> auditContextStore;
  private final AuditDataStore<A> dataStore;

  public AuditErrorDecoder(AuditContextStore<A> auditContextStore, AuditDataStore<A> dataStore) {
    this.auditContextStore = auditContextStore;
    this.dataStore = dataStore;
  }

  @Override
  public Exception decode(String methodKey, Response response) {
    try {
      var message = auditContextStore.setAuditContext(methodKey, response);
      dataStore.saveAuditData(auditContextStore.getAuditContext());
      if (response.status() >= 400 && response.status() < 500) {
        return new FourXXErrorDecoderException(message);
      } else if (response.status() >= 500) {
        return new FiveXXErrorDecoderException(message);
      } else {
        return new Exception(message);
      }
    } catch (AuditRequestContextException e) {
      throw new RuntimeException(e);
    }
  }
}
