package com.phonepe.payments.fundtransfer.codec;

import com.phonepe.payments.fundtransfer.codec.model.DeleteHeader;
import com.phonepe.payments.fundtransfer.codec.model.DeletePath;
import com.phonepe.payments.fundtransfer.codec.model.DeleteQuery;
import com.phonepe.payments.fundtransfer.codec.model.DeleteUser;
import com.phonepe.payments.fundtransfer.codec.model.GetHeader;
import com.phonepe.payments.fundtransfer.codec.model.GetPath;
import com.phonepe.payments.fundtransfer.codec.model.GetQuery;
import com.phonepe.payments.fundtransfer.codec.model.PostUserBody;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

@Headers("content-type: application/json")
public interface ExternalServiceClient {

  @RequestLine("GET /api/users")
  Response getUser();

  @RequestLine("GET /api/users/header")
  @Headers({"TRANSACTION_ID: {transactionId}"})
  GetHeader getUserWithHeader(@Param("transactionId") String transactionId);

  @RequestLine("GET /api/users/param?transactionId={transactionId}")
  GetQuery getUserWithQuery(@Param("transactionId") String transactionId);

  @RequestLine("GET /api/users/path/{transactionId}")
  GetPath getUserWithPathParam(@Param("transactionId") String userId);

  @RequestLine("DELETE /api/users")
  DeleteUser deleteUsers();

  @RequestLine("DELETE /api/users/header")
  @Headers({"TRANSACTION_ID: {transactionId}"})
  DeleteHeader deleteUserWithHeader(@Param("transactionId") String transactionId);

  @RequestLine("DELETE /api/users/param?transactionId={transactionId}")
  DeleteQuery deleteUserWithQuery(@Param("transactionId") String transactionId);

  @RequestLine("DELETE /api/users/path/{transactionId}")
  DeletePath deleteUserWithPathParam(@Param("transactionId") String userId);

  @RequestLine("HEAD /api/users")
  Response headUsers();

  @RequestLine("HEAD /api/users/header")
  @Headers({"TRANSACTION_ID: {transactionId}"})
  Response headUserWithHeader(@Param("transactionId") String transactionId);

  @RequestLine("HEAD /api/users/param?transactionId={transactionId}")
  Response headUserWithQuery(@Param("transactionId") String transactionId);

  @RequestLine("HEAD /api/users/path/{transactionId}")
  Response headUserWithPathParam(@Param("transactionId") String userId);

  @RequestLine("POST /api/users")
  Response postUser();

  @RequestLine("POST /api/users/body")
  Response postUserWithBody(PostUserBody postUserBody);

  @RequestLine("POST /api/users/header")
  @Headers({"TRANSACTION_ID: {transactionId}"})
  Response postUserWithHeader(@Param("transactionId") String transactionId);

  @RequestLine("POST /api/users/param?transactionId={transactionId}")
  Response postUserWithQuery(@Param("transactionId") String transactionId);

  @RequestLine("POST /api/users/path/{transactionId}")
  Response postUserWithPathParam(@Param("transactionId") String userId);

  @RequestLine("POST /api/users/body/without/response/type")
  PostUserBody postUserWithBodyWithoutResponseType(PostUserBody postUserBody);

  @RequestLine("GET /api/error/4xx")
  void getError4xx();

  @RequestLine("POST /api/error/4xx")
  void postError4xx();

  @RequestLine("GET /api/error/5xx")
  void getError5xx();

  @RequestLine("POST /api/error/5xx")
  void postError5xx();

  @RequestLine("POST /api/error/5xx/body")
  void postError5xxWithBody(PostUserBody postUserBody);

  @RequestLine("POST /api/error/4xx/body")
  void postError4xxWithBody(PostUserBody postUserBody);

  @RequestLine("PUT /api/users")
  Response putUser();

  @RequestLine("PUT /api/users/body")
  Response putUserWithBody(PostUserBody postUserBody);

  @RequestLine("PUT /api/users/header")
  @Headers({"TRANSACTION_ID: {transactionId}"})
  Response putUserWithHeader(@Param("transactionId") String transactionId);

  @RequestLine("PUT /api/users/param?transactionId={transactionId}")
  Response putUserWithQuery(@Param("transactionId") String transactionId);

  @RequestLine("PUT /api/users/path/{transactionId}")
  Response putUserWithPathParam(@Param("transactionId") String userId);

  @RequestLine("PUT /api/users/body/without/response/type")
  PostUserBody putUserWithBodyWithoutResponseType(PostUserBody postUserBody);

  // Add the annotation @Transformer(name="postUserWithBodyTransformImpl")
  @AuditTransformer(value = "postUserWithBodyTransform")
  @RequestLine("POST /api/users/body/transform")
  PostUserBody postUserWithBodyTransform(PostUserBody postUserBody);

  @AuditTransformer(value = "postUserWithBodyTransform")
  @RequestLine("POST /api/users/body/transform")
  PostUserBody postUserWithBodyTransformSecond(PostUserBody postUserBody);

  @AuditTransformer(value = "postUserWithBodyTransform")
  @RequestLine("POST /api/users/body/transform")
  Response postUserWithBodyTransformSecondWithResponseReturn(PostUserBody postUserBody);
}