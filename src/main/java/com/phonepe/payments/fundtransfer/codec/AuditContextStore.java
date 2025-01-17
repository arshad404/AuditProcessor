package com.phonepe.payments.fundtransfer.codec;

import feign.RequestTemplate;
import java.lang.reflect.Type;

public interface AuditContextStore<T extends AuditContext> {
  void setAuditContext(Object o, Type type, RequestTemplate requestTemplate) throws AuditRequestContextException;
  T getAuditContext() throws AuditRequestContextException;
}
