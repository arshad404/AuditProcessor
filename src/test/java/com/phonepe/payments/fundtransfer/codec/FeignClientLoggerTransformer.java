package com.phonepe.payments.fundtransfer.codec;

import static java.util.Objects.nonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.codec.model.PostUserBody;
import feign.Response;
import feign.Util;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignClientLoggerTransformer {

  @AuditTransformer(name = "postUserWithBodyTransform")
  public Response transformResponseInLogger(Response response) {
    try {
      // Step 1: Read the raw body
      String rawBody = Util.toString(response.body().asReader(Util.UTF_8));

      if (nonNull(response.body().length())) {
        log.warn("Empty response body string. Skipping transformation.");
        return response;
      }

      // Step 2: Deserialize the raw body to PostUserBody
      ObjectMapper objectMapper = new ObjectMapper(); // You can configure it as needed
      PostUserBody postUserBody = objectMapper.readValue(rawBody, PostUserBody.class);
      postUserBody.setTransactionId("%s_LOGTRANSFORMED".formatted(postUserBody.getTransactionId()));

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
