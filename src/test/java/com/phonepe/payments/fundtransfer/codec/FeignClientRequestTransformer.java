package com.phonepe.payments.fundtransfer.codec;

import com.phonepe.payments.fundtransfer.codec.model.PostUserBody;
import feign.RequestTemplate;
import java.lang.reflect.Type;

public class FeignClientRequestTransformer {

  @AuditTransformer(name = "postUserWithBodyTransform")
  public Object transformRequest(Object o, Type type, RequestTemplate requestTemplate) {
    PostUserBody postUserBody = (PostUserBody) o;
    postUserBody.setTransactionId("%s123".formatted(postUserBody.getTransactionId()));
    return o;
  }
}
