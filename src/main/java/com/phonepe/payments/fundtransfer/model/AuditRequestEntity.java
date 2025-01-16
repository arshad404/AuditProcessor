package com.phonepe.payments.fundtransfer.model;

public class AuditRequestEntity {

  private String id;
  private byte[] request;
  private String requestEntityType;
  private String method;
  private String requestPath;

  public AuditRequestEntity(String id, byte[] request, String requestEntityType, String method,
      String requestPath) {
    this.id = id;
    this.request = request;
    this.requestEntityType = requestEntityType;
    this.method = method;
    this.requestPath = requestPath;
  }

  public String getId() {
    return id;
  }

  public byte[] getRequest() {
    return request;
  }

  public String getRequestEntityType() {
    return requestEntityType;
  }

  public String getMethod() {
    return method;
  }

  public String getRequestPath() {
    return requestPath;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setRequest(byte[] request) {
    this.request = request;
  }

  public void setRequestEntityType(String requestEntityType) {
    this.requestEntityType = requestEntityType;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public void setRequestPath(String requestPath) {
    this.requestPath = requestPath;
  }
}
