package com.phonepe.payments.fundtransfer.codec_2;

import com.phonepe.payments.fundtransfer.codec_1.AuditRequestContextException;
import feign.RequestTemplate;
import java.lang.reflect.Type;

public interface AuditContextStore<T> {
  void setAuditContext(Object o, Type type, RequestTemplate requestTemplate)
      throws AuditRequestContextException;
  T getAuditContext(String key, Class<T> valueType);
}
