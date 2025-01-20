package com.phonepe.payments.fundtransfer.codec;

import com.phonepe.payments.fundtransfer.exceptions.AuditRequestContextException;
import feign.RequestTemplate;
import feign.Response;
import java.lang.reflect.Type;

public interface IAuditContextStore<T extends IAuditContext> {
  // Request
  void setAuditContext(Object o, Type type, RequestTemplate requestTemplate) throws AuditRequestContextException;
  // Response
  void setAuditContext(Object object, Response response, Type type);
  // Error
  T getAuditContext() throws AuditRequestContextException;
}
