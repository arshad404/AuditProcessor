package com.phonepe.payments.fundtransfer.codec;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Collection;
import java.util.Map;

public abstract class AuditRequestInterceptor<A extends AuditContext> implements
    RequestInterceptor {

  private final AuditContextStore<A> auditContextStore;

  public AuditRequestInterceptor(AuditContextStore<A> auditContextStore) {
    this.auditContextStore = auditContextStore;
  }

  @Override
  public void apply(RequestTemplate template) {
    // Capture request headers, query params, method and URL
    String url = template.url();
    Map<String, Collection<String>> headers = template.headers();
    String queryParams = template.queryLine();
    String method = template.method();

    auditContextStore.setAuditContext(template);

    System.out.println(
        "Captured Request: URL=" + url + ", Headers=" + headers + ", QueryParams=" + queryParams
            + ", Method=" + method);
  }
}
