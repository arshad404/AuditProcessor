package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.exceptions.AuditRequestContextException;
import feign.RequestTemplate;
import feign.Response;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public class TestAuditContextStore implements IAuditContextStore<DefaultAuditContext> {


  private final DefaultContextStore defaultContextStore;
  ObjectMapper objectMapper = new ObjectMapper();

  // Constructor accepting an already created DefaultContextStore for better flexibility | Enabling DI
  public TestAuditContextStore(DefaultContextStore defaultContextStore) {
    this.defaultContextStore = defaultContextStore;
  }

  // Alternatively, constructor that creates a DefaultContextStore internally, using the ObjectMapper
  public TestAuditContextStore(ObjectMapper objectMapper) {
    this.defaultContextStore = new DefaultContextStore(objectMapper);
  }

  @Override
  public void setAuditContext(Object o, Type type, RequestTemplate requestTemplate)
      throws AuditRequestContextException {
    try {
      var auditContext = DefaultAuditContext.builder()
          .id("key123")
          .headers(requestTemplate.headers())
          .method(requestTemplate.method())
          .url(requestTemplate.url())
          .requestObject(o).build();
      auditContext.setTypeFromType(type);
      auditContext.setRequestDataFromByte(objectMapper.writeValueAsBytes(o));
      defaultContextStore.setContext(auditContext);
    } catch (Exception e) {
      throw new AuditRequestContextException("error while setting the default context", e);
    }
  }

  @Override
  public void setAuditContext(Object object, Response response, Type type) {
//    defaultContextStore.setContext(auditContext);
  }

  @Override
  public DefaultAuditContext getAuditContext() throws AuditRequestContextException {
    try {
      return defaultContextStore.getContext();
    } catch (Exception e) {
      throw new AuditRequestContextException("error while getting the default context", e);
    }
  }

  private String getHeaderValue(RequestTemplate template, String headerName) {
    Map<String, Collection<String>> headers = template.headers();
    Collection<String> values = headers.get(headerName);
    if (values != null && !values.isEmpty()) {
      return values.iterator().next();
    }
    return null;
  }
}