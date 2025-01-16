package com.phonepe.payments.fundtransfer.codec_1;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuditErrorDecoder implements ErrorDecoder {

  private final AuditContextStoreService auditContextStoreService;

  public AuditErrorDecoder(AuditContextStoreService auditContextStoreService) {
    this.auditContextStoreService = auditContextStoreService;
  }

  @Override
  public Exception decode(String methodKey, Response response) {

    String auditRequestContext = auditContextStoreService.getAuditRequestContext(Constants.MDCKey);

    return null;
  }
}