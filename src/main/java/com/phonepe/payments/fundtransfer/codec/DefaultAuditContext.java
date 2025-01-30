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
import lombok.SneakyThrows;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DefaultAuditContext implements AuditContext {

  private String id;
  private String requestData;
  private String type;
  private String method;
  private String url;
  private Object requestObject;
  private Map<String, Collection<String>> headers;

  private String responseData;
  private int statusCode;
  private String reason;

  public void setRequestDataFromByte(byte[] data) {
    this.requestData = Base64.getEncoder().encodeToString(data);
  }

  public void setTypeFromType(Type type) {
    this.type = type.getTypeName();
  }

  @JsonIgnore
  public byte[] getRequestDataFromByte() {
    return Base64.getDecoder().decode(requestData);
  }

  @JsonIgnore
  @SneakyThrows
  public Type getTypeFromType() {
    return Class.forName(this.type);
  }

  @Override
  public String getId() {
    return "DefaultAuditContextKey";
  }

  public static DefaultAuditContext getDefaultAuditContext() {
    return new DefaultAuditContext();
  }
}
