package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.codec.model.PostUserBody;
import feign.RequestTemplate;
import feign.Response;
import feign.Util;
import java.io.IOException;
import java.lang.reflect.Type;

public class PostUserWithBodyTransformAnnotation implements Transformer {

  public String value() {
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

  @Override
  public Response transformResponseInLogger(Response response) {
    try {
      // Step 1: Read the raw body
      String rawBody = Util.toString(response.body().asReader(Util.UTF_8));

      // Step 2: Deserialize the raw body to PostUserBody
      ObjectMapper objectMapper = new ObjectMapper(); // You can configure it as needed
      PostUserBody postUserBody = objectMapper.readValue(rawBody, PostUserBody.class);
      postUserBody.setTransactionId(postUserBody.getTransactionId() + "_LOGTRANSFORMED");

      // Step 4: Serialize the transformed body back to JSON
      String transformedBodyJson = objectMapper.writeValueAsString(postUserBody);

      // Step 5: Build a new Response with the transformed body
      return response.toBuilder()
          .body(transformedBodyJson, Util.UTF_8)
          .build();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
