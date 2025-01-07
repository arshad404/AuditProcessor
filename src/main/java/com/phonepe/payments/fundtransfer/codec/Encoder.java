package com.phonepe.payments.fundtransfer.codec;

import com.phonepe.payments.fundtransfer.compression.Compression;
import com.phonepe.payments.fundtransfer.database.AuditData;
import com.phonepe.payments.fundtransfer.model.AuditProcessorConfig;
import feign.RequestTemplate;
import java.lang.reflect.Type;

public abstract class Encoder implements feign.codec.Encoder {
  private final Compression compression;
  private final AuditData auditData;
  private final AuditProcessorConfig auditProcessorConfig;

  protected Encoder(Compression compression, AuditData auditData, AuditProcessorConfig auditProcessorConfig) {
    this.compression = compression;
    this.auditData = auditData;
    this.auditProcessorConfig = auditProcessorConfig;
  }

  @Override
  public void encode(Object o, Type type, RequestTemplate requestTemplate) {
    // Extract the data that need to be logged
    byte[] extractedRequestDataForAuditing = this.extract(o,type, requestTemplate);
    // Check if auditing is enabled then audit the request
    if(!auditProcessorConfig.isDisableAuditing()) {
      if(!auditProcessorConfig.isDisableCompression()) {
        extractedRequestDataForAuditing = this.compression.compress(extractedRequestDataForAuditing);
      }
      audit(requestTemplate, extractedRequestDataForAuditing);
    }
    // Transform request can be by removing some header or any changes in the request that the user want to do
    // TODO: discuss to change the name of the transform to process, no usage of transform if we already have the extract method
    this.transform(o, type, requestTemplate);
  }

  protected abstract byte[] extract(Object o, Type type, RequestTemplate requestTemplate);

  // In the transform method, you can use the encoder of your choice like JacksonEncoder
  // Keeping the return type as void as otherwise we end up copying and pasting the whole codebase, even encoder and JacksonEncoder have the same void return type
  protected abstract void transform(Object o, Type type, RequestTemplate requestTemplate);

  protected void audit(RequestTemplate requestTemplate, byte[] data) {
    auditData.save(requestTemplate, data);
  }
}

