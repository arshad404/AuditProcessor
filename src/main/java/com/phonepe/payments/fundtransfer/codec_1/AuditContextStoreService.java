package com.phonepe.payments.fundtransfer.codec_1;

import feign.RequestTemplate;
import java.lang.reflect.Type;

public interface AuditContextStoreService {
  void setAuditRequestContext(Object o, Type type, RequestTemplate requestTemplate)
      throws AuditRequestContextException;
  String getAuditRequestContext(String key);
}
