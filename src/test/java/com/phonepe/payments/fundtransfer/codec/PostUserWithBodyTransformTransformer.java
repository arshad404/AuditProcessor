package com.phonepe.payments.fundtransfer.codec;

import com.phonepe.payments.fundtransfer.codec.model.PostUserBody;
import feign.RequestTemplate;
import feign.Response;
import java.lang.reflect.Type;

public class PostUserWithBodyTransformTransformer implements Transformer {

  public String methodName() {
    return "postUserWithBodyTransform";
  }

  @Override
  public Object transformRequest(Object o, Type type, RequestTemplate requestTemplate) {
    PostUserBody postUserBody = (PostUserBody) o;
    postUserBody.setTransactionId(postUserBody.getTransactionId() + "123");
    return o;
  }

  @Override
  public Object transformResponse(Object o, Type type, Response response) {
    PostUserBody postUserBody = (PostUserBody) o;
    postUserBody.setTransactionId(postUserBody.getTransactionId() + "_TRANSFORMED");
    return o;
  }
}
