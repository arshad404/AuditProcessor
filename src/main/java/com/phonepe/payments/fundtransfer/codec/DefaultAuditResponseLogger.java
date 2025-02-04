package com.phonepe.payments.fundtransfer.codec;

import feign.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

public class DefaultAuditResponseLogger extends AuditResponseLogger<DefaultAuditContext> {

  public DefaultAuditResponseLogger(DefaultRequestContextManager requestContextManager,
      AuditDataStore<DefaultAuditContext> auditDataStore) {
    super(requestContextManager, auditDataStore);
  }

  @Override
  protected void updateAuditContext(DefaultAuditContext auditContext, Response response,
      Boolean isDecoderSkipped) {
    try {
      // Set status code and reason
      auditContext.setStatusCode(response.status());
      auditContext.setReason(response.reason());
      if (Boolean.TRUE.equals(isDecoderSkipped)) {
        // Copy the response input stream before consuming it
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(response.body().asInputStream(), byteArrayOutputStream);

        // Convert the copied stream to a byte array
        byte[] bodyData = byteArrayOutputStream.toByteArray();
        response.toBuilder().body(bodyData).build();

        // Store the response data in the audit context
        auditContext.setResponseData(bodyData);
      }
    } catch (IOException e) {
      throw new CodecException("error in updating the context in logger", e);
    }
  }
}
