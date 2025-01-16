package com.phonepe.payments.fundtransfer.codec_1;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import java.lang.reflect.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class AuditEncoder implements Encoder {
  private final AuditContextStoreService auditContextStoreService;
  private final TransformService transformService;
  // Developer can use any encoder of their choice
  private final Encoder encoder;


  @Override
  public void encode(Object object, Type bodyType, RequestTemplate template)
      throws EncodeException {
    try {
      auditContextStoreService.setAuditRequestContext(object, bodyType, template);
      transformService.transformRequest(object, bodyType, template);
      encoder.encode(object, bodyType, template);
    } catch (AuditRequestContextException e) {
      throw new EncodeException(e.getMessage(), e);
    }
  }
}
