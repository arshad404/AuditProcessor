package com.phonepe.payments.fundtransfer.codec;

import static java.util.Objects.isNull;

import org.apache.log4j.MDC;

public class DefaultRequestContextManager implements RequestContextManager<DefaultAuditContext> {

  private static final String AUDIT_CONTEXT_KEY = "audit-context-key";

  private static final DefaultRequestContextManager INSTANCE = new DefaultRequestContextManager();

  private DefaultRequestContextManager() {
    //Hidden constructor
  }

  public static DefaultRequestContextManager getInstance() {
    return INSTANCE;
  }
  
  @Override
  public DefaultAuditContext getContext() {
    var storedContext = MDC.get(AUDIT_CONTEXT_KEY);
    if (isNull(storedContext)) {
      return DefaultAuditContext.builder().build();
    }
    return (DefaultAuditContext) storedContext;
  }

  @Override
  public void setContext(DefaultAuditContext context) {
    MDC.put(AUDIT_CONTEXT_KEY, context);
  }
}
