package com.phonepe.payments.fundtransfer.codec_new;

import feign.Client;
import feign.Request;
import feign.Response;
import java.io.IOException;

public class CustomFeignClient extends Client.Default {

  private final RequestContextManager<DefaultAuditContext> requestContextManager;

  public CustomFeignClient(RequestContextManager<DefaultAuditContext> requestContextManager) {
    super(null, null);  // Uses default HTTP client
    this.requestContextManager = requestContextManager;
  }

  @Override
  public Response execute(Request request, Request.Options options) throws IOException {
    System.out.println(request.url());
    Response response = super.execute(request, options);

    // Capture response status in audit context
    var context = requestContextManager.getContext();
    context.setStatusCode(response.status());
    requestContextManager.setContext(context);

    return response;
  }
}
