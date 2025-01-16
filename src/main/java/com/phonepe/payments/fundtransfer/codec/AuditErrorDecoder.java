package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.model.AuditRequestEntity;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.util.logging.Logger;
import org.slf4j.MDC;

public abstract class AuditErrorDecoder extends BaseDecoder implements ErrorDecoder {

  protected final Logger logger = Logger.getLogger("AuditLog");

  protected AuditErrorDecoder(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public Exception decode(String methodKey, Response response) {
    try {
      var exceptionResponse = decodeErrorResponse(methodKey, response);
      var auditContext = objectMapper.readValue(MDC.get("AUDIT_CONTEXT"), AuditRequestEntity.class);
      saveAuditData(exceptionResponse,AuditException.class, auditContext);
      throw new AuditException(exceptionResponse, response, methodKey);
    } catch(IOException e) {
      throw new AuditException(null, response, methodKey);
    }
  }

  protected Object decodeErrorResponse(String methodKey, Response response) throws IOException  {
    return objectMapper.readTree(response.body().asInputStream());
  }
}
