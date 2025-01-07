package com.phonepe.payments.fundtransfer.codec;

import com.phonepe.payments.fundtransfer.compression.Compression;
import com.phonepe.payments.fundtransfer.database.AuditData;
import com.phonepe.payments.fundtransfer.model.AuditProcessorConfig;
import feign.Response;
import java.lang.reflect.Type;
import org.slf4j.MDC;

public abstract class Decoder implements feign.codec.Decoder {
  private final Compression compression;
  private final AuditData auditData;
  private final AuditProcessorConfig auditProcessorConfig;

  public Decoder(Compression compression, AuditData auditData, AuditProcessorConfig auditProcessorConfig) {
    this.compression = compression;
    this.auditData = auditData;
    this.auditProcessorConfig = auditProcessorConfig;
  }

  /*
  decode:
   */
  @Override
  public Object decode(Response response, Type type) {
    Object responseObject = transform(response, type);

    var dataToAudit = extract(responseObject, type);

    if(!auditProcessorConfig.isDisableAuditing()) {
      if(!auditProcessorConfig.isDisableCompression()) {
        dataToAudit = this.compression.compress(dataToAudit);
      }
      audit(response, dataToAudit);
    }

    return responseObject;
  }

  protected abstract byte[] extract(Object object, Type type);

  // In the transform method, you can use the encoder of your choice like JacksonEncoder
  protected abstract Object transform(Response response, Type type);

  protected void audit(Response response, byte[] data) {
    auditData.update(response, MDC.get("X-FT-TRANSACTION-ID"), data); // how to fetch the transaction id from the response, we need the key for update
  }
}

