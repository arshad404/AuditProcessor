package com.phonepe.payments.fundtransfer.codec_new;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Getter;

@Getter
public abstract class AuditRequestInterceptor<T extends AuditContext> implements
    RequestInterceptor {

  private final RequestContextManager<T> requestContextManager;

  protected AuditRequestInterceptor(RequestContextManager<T> requestContextManager) {
    this.requestContextManager = requestContextManager;
  }

  @Override
  public void apply(RequestTemplate template) {
    T auditContext = setAuditContext(template);
    requestContextManager.setContext(auditContext);
  }

  protected abstract T setAuditContext(RequestTemplate template);
}
