package com.phonepe.payments.fundtransfer.codec;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.phonepe.payments.fundtransfer.codec.model.DeleteHeader;
import com.phonepe.payments.fundtransfer.codec.model.DeletePath;
import com.phonepe.payments.fundtransfer.codec.model.DeleteQuery;
import com.phonepe.payments.fundtransfer.codec.model.DeleteUser;
import com.phonepe.payments.fundtransfer.codec.model.GetHeader;
import com.phonepe.payments.fundtransfer.codec.model.GetPath;
import com.phonepe.payments.fundtransfer.codec.model.GetQuery;
import com.phonepe.payments.fundtransfer.codec.model.PostUserBody;
import feign.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class GetAuditProcessorTest extends BaseAuditProcessorTest {

  @BeforeAll
  void Init() {
    setupWireMock();
  }

  @Test
  @Order(0)
  void TestGetUser() {
    Response response = externalServiceClient.getUser();
    assertEquals(200, response.status());
    assertEquals(response.headers().get("content-type").iterator().next(), "application/json");
  }

  @Test
  @Order(1)
  void TestGetUserWithHeader() {
    GetHeader response = externalServiceClient.getUserWithHeader("txn123");
    assertEquals("response_with_header", response.getHeader());
    assertEquals("txn123", response.getTransactionId());
  }

  @Test
  @Order(2)
  void TestGetUserWithQueryParam() {
    GetQuery response = externalServiceClient.getUserWithQuery("txn123");
    assertEquals("response_with_query_param", response.getQuery());
    assertEquals("txn123", response.getTransactionId());
  }

  @Test
  @Order(3)
  void TestGetUserWithPathParam() {
    GetPath response = externalServiceClient.getUserWithPathParam("txn123");
    assertEquals("response_with_path_param", response.getPath());
    assertEquals("txn123", response.getTransactionId());
  }

  // ---------------- DELETE --------------------------

  @Test
  @Order(4)
  void TestDeleteUser() {
    DeleteUser response = externalServiceClient.deleteUsers();
    assertEquals("123", response.getUserId());
    assertEquals("txn123", response.getTransactionId());
  }

  @Test
  @Order(5)
  void TestDeleteUserWithHeader() {
    DeleteHeader response = externalServiceClient.deleteUserWithHeader("txn123");
    assertEquals("response_with_header", response.getHeader());
    assertEquals("txn123", response.getTransactionId());
  }

  @Test
  @Order(6)
  void TestDeleteUserWithQueryParam() {
    DeleteQuery response = externalServiceClient.deleteUserWithQuery("txn123");
    assertEquals("response_with_query_param", response.getQuery());
    assertEquals("txn123", response.getTransactionId());
  }

  @Test
  @Order(7)
  void TestDeleteUserWithPathParam() {
    DeletePath response = externalServiceClient.deleteUserWithPathParam("txn123");
    assertEquals("response_with_path_param", response.getPath());
    assertEquals("txn123", response.getTransactionId());
  }

  // ---------------- HEAD --------------------------

  @Test
  @Order(8)
  void TestHEADUser() {
    Response response = externalServiceClient.headUsers();
    assertEquals(200, response.status());
    assertEquals(response.headers().get("content-type").iterator().next(), "application/json");
  }

  @Test
  @Order(9)
  void TestHEADUserWithHeader() {
    Response response = externalServiceClient.headUserWithHeader("txn123");
    String transactionId = response.headers().get("TRANSACTION_ID").iterator().next();
    assertEquals("txn123", transactionId);

    String method = response.headers().get("method").iterator().next();
    assertEquals("head", method);
  }

  @Test
  @Order(10)
  void TestHEADUserWithQueryParam() {
    Response response = externalServiceClient.headUserWithQuery("txn123");
    String transactionId = response.headers().get("TRANSACTION_ID").iterator().next();
    assertEquals("txn456", transactionId);

    String method = response.headers().get("method").iterator().next();
    assertEquals("head", method);
  }

  @Test
  @Order(11)
  void TestHEADUserWithPathParam() {
    Response response = externalServiceClient.headUserWithPathParam("txn123");
    String transactionId = response.headers().get("TRANSACTION_ID").iterator().next();
    assertEquals("txn789", transactionId);

    String method = response.headers().get("method").iterator().next();
    assertEquals("head", method);
  }

  @Test
  @Order(12)
  void TestPOSTUserWithBody() {
    Response response = externalServiceClient.postUserWithBody(
        new PostUserBody("txn123", "post"));
    System.out.println(response.status());
//    String transactionId = response.headers().get("TRANSACTION_ID").iterator().next();
//    assertEquals("txn789", transactionId);
//
//    String method = response.headers().get("method").iterator().next();
//    assertEquals("head", method);
  }
}
