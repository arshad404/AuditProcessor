package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.model.AuditRequestEntity;
import feign.Response;
import feign.codec.Decoder;
import java.io.IOException;
import java.lang.reflect.Type;
import org.slf4j.MDC;

public abstract class AuditDecoder extends BaseDecoder implements Decoder {


  protected AuditDecoder(ObjectMapper objectMapper) {
    super(objectMapper);
  }
  /*
  decode:
   */
  @Override
  public Object decode(Response response, Type type) {
    try {
      // RED: Type here is of response type and we are unmarshalling it with request string
      var auditContext = getAuditContext(response, type);
      var decodedResponse = decodeResponse(response, type);
      saveAuditData(decodedResponse, type, auditContext);
      return decodedResponse;
    } catch (Exception e) {
      return new RuntimeException(e);
    }
  }

  //Make this Generic to allow user to implement custom audit entity
  protected AuditRequestEntity getAuditContext(Response response, Type type)
      throws JsonProcessingException {
    return objectMapper.readValue(MDC.get("AUDIT_CONTEXT"), AuditRequestEntity.class);
  }

  protected Object decodeResponse(Response response, Type type) throws IOException {
    return objectMapper.readValue(response.body().asInputStream(), type.getClass());
  }
}

