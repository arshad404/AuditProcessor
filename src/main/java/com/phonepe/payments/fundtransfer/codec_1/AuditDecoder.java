package com.phonepe.payments.fundtransfer.codec_1;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import java.io.IOException;
import java.lang.reflect.Type;

public class AuditDecoder implements Decoder {
  private final AuditContextStoreService auditContextStoreService;
  private final TransformService transformService;
  private final SinkService sinkService;

  public AuditDecoder(AuditContextStoreService auditContextStoreService,
      TransformService transformService, SinkService sinkService) {
    this.auditContextStoreService = auditContextStoreService;
    this.transformService = transformService;
    this.sinkService = sinkService;
  }


  // 1. Fetch the response object
  // 2. Decrypt the object
  // 3. Fetch the body that need to be logged
  // 4. return full object that need to be returned
  @Override
  public Object decode(Response response, Type type)
      throws IOException, DecodeException, FeignException {
    // GET REQUEST DATA
    String auditRequestContext = auditContextStoreService.getAuditRequestContext(Constants.MDCKey);
    // 1. UNMARSHALL THE BYTE[] to Object
    // 2. Decrypt the object
    // 3. UNMARSHALL THE DECRYPT BYTE[] to object
    // 4. RETURN OBJECT
    Object decodedResponse = this.transformService.decodeResponse(response, type);
    sinkService.saveAuditData(auditRequestContext, decodedResponse);
    return decodedResponse;
  }
}
