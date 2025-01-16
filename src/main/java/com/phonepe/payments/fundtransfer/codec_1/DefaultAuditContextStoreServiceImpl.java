package com.phonepe.payments.fundtransfer.codec_1;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestTemplate;
import java.lang.reflect.Type;
import java.util.Collection;

public class DefaultAuditContextStoreServiceImpl implements AuditContextStoreService {

  private final ContextStoreService contextStoreService;
  private final ObjectMapper objectMapper;

  DefaultAuditContextStoreServiceImpl() {
    this.contextStoreService = new ContextStoreServiceImpl();
    this.objectMapper = new ObjectMapper();
  }

  @Override
  public void setAuditRequestContext(Object o, Type type, RequestTemplate requestTemplate)
      throws AuditRequestContextException {
    try {
      Collection<String> keyIdHeader = requestTemplate.headers().get(Constants.MDCKey);
      if (keyIdHeader != null && !keyIdHeader.isEmpty()) {
        String key = keyIdHeader.iterator().next();  // Get the first element
          ContextStoreDao contextStoreDao = new ContextStoreDao(key, requestTemplate.body(), type,
              requestTemplate.method(), requestTemplate.url());
          contextStoreService.setContext(key, objectMapper.writeValueAsString(contextStoreDao));
      }
    } catch (Exception e) {
      throw new AuditRequestContextException("Exception while saving the request context", e);
    }

  }

  @Override
  public String getAuditRequestContext(String key) {
    return contextStoreService.getContext(key);
  }
}
