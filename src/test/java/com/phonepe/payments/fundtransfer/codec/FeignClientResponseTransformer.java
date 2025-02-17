package com.phonepe.payments.fundtransfer.codec;

import com.phonepe.payments.fundtransfer.codec.model.PostUserBody;
import feign.Response;
import java.lang.reflect.Type;

public class FeignClientResponseTransformer {

  @AuditTransformer(name = "postUserWithBodyTransform")
  public Object transformResponse(Object o, Type type, Response response) {
    PostUserBody postUserBody = (PostUserBody) o;
    postUserBody.setTransactionId("%s_TRANSFORMED".formatted(postUserBody.getTransactionId()));
    return o;
  }

}
