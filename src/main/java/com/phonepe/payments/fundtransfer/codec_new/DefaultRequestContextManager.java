package com.phonepe.payments.fundtransfer.codec_new;

import org.apache.log4j.MDC;

public class DefaultRequestContextManager implements
    RequestContextManager<DefaultAuditContext> {

  private static final String AUDIT_CONTEXT_KEY = "audit-context-key";

  @Override
  public DefaultAuditContext getContext() {
    var storedContext = MDC.get(AUDIT_CONTEXT_KEY);
    if (storedContext == null) {
      return DefaultAuditContext.builder().build();
    }
    return (DefaultAuditContext) storedContext;
  }

  @Override
  public void setContext(DefaultAuditContext context) {
    MDC.put(AUDIT_CONTEXT_KEY, context);
  }

  @Override
  public void clearContext() {
    MDC.clear();
  }
}
