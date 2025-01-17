package com.phonepe.payments.fundtransfer.codec_2;

import java.lang.reflect.Type;
import java.util.Base64;
import java.util.Collection;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class DefaultAuditContext implements AuditContext {

  private String id;

  private String requestData;

  private Type type;

  private String method;

  private String url;

  private Object requestObject;

  private Map<String, Collection<String>> headers;

  public void setRequestData(byte[] data) {
    this.requestData = Base64.getEncoder().encodeToString(data);
  }

  public byte[] getRequestData() {
    return Base64.getDecoder().decode(requestData);
  }
}
