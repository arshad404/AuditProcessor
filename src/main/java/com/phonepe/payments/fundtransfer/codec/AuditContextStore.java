package com.phonepe.payments.fundtransfer.codec;

import com.phonepe.payments.fundtransfer.exceptions.AuditRequestContextException;
import feign.RequestTemplate;
import feign.Response;
import java.lang.reflect.Type;

public interface AuditContextStore<T extends AuditContext> {
  // Request
  void setAuditContext(Object o, Type type, RequestTemplate requestTemplate) throws AuditRequestContextException;
  // Response
  void setAuditContext(Object object, Response response, Type type);
  // Error Response
  String setAuditContext(String methodKey, Response response);
  // Get the Stored AuditContext
  T getAuditContext() throws AuditRequestContextException;
}
