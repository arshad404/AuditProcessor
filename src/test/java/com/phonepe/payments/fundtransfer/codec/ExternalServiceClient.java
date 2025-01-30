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

  @RequestLine("GET /api/users/path/{userId}")
  GetPath getUserWithPathParam(@Param("userId") String userId);

  @RequestLine("DELETE /api/users")
  DeleteUser deleteUsers();

  @RequestLine("DELETE /api/users/header")
  @Headers({"TRANSACTION_ID: {transactionId}"})
  DeleteHeader deleteUserWithHeader(@Param("transactionId") String transactionId);

  @RequestLine("DELETE /api/users/param?transactionId={transactionId}")
  DeleteQuery deleteUserWithQuery(@Param("transactionId") String transactionId);

  @RequestLine("DELETE /api/users/path/{userId}")
  DeletePath deleteUserWithPathParam(@Param("userId") String userId);

  @RequestLine("HEAD /api/users")
  Response headUsers();

  @RequestLine("HEAD /api/users/header")
  @Headers({"TRANSACTION_ID: {transactionId}"})
  Response headUserWithHeader(@Param("transactionId") String transactionId);

  @RequestLine("HEAD /api/users/param?transactionId={transactionId}")
  Response headUserWithQuery(@Param("transactionId") String transactionId);

  @RequestLine("HEAD /api/users/path/{userId}")
  Response headUserWithPathParam(@Param("userId") String userId);

  @RequestLine("POST /api/users/body")
  Response postUserWithBody(PostUserBody postUserBody);
}