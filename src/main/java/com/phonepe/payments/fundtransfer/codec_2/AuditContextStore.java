package com.phonepe.payments.fundtransfer.codec_2;

import feign.RequestTemplate;
import java.lang.reflect.Type;

public interface AuditContextStore<T extends AuditContext> {
  void setAuditContext(Object o, Type type, RequestTemplate requestTemplate)
      throws AuditRequestContextException;
  T getAuditContext();
}
