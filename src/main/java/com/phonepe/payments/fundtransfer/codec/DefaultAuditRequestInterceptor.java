package com.phonepe.payments.fundtransfer.codec;

import feign.RequestTemplate;

public class DefaultAuditRequestInterceptor extends AuditRequestInterceptor<DefaultAuditContext> {

  public DefaultAuditRequestInterceptor(
      DefaultRequestContextManager defaultRequestContextManager) {
    super(defaultRequestContextManager);
  }

  @Override
  protected DefaultAuditContext setAuditContext(RequestTemplate template) {
    var context = this.getRequestContextManager().getContext();
    context.setId(this.getRequestContextManager().getContext().getId());
    context.setUrl(template.url());
    context.setHeaders(template.headers());
    context.setQueryParams(template.queryLine());
    context.setMethod(template.method());
    return context;
  }
}
