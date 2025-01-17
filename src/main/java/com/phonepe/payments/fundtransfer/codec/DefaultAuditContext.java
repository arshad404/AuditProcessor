package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.Collection;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DefaultAuditContext implements AuditContext {

  private String id;
  private String utr;
  private String requestData;
  private String type;
  private String method;
  private String url;
  private Object requestObject;
  private Map<String, Collection<String>> headers;

  public void setRequestData(byte[] data) {
    this.requestData = Base64.getEncoder().encodeToString(data);
  }

  public void setType(Type type) {
    this.type = type != null ? type.getTypeName() : null;
  }

  @JsonIgnore
  public Type getType() throws ClassNotFoundException {
    return type != null ? Class.forName(type) : null;
  }

  public byte[] getRequestData() {
    return Base64.getDecoder().decode(requestData);
  }

  @Override
  public String getId() {
    return "DefaultAuditContextKey";
  }
}
