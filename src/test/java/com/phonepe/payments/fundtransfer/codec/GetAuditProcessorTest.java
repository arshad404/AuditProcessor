package com.phonepe.payments.fundtransfer.codec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
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
    assertEquals("application/json", response.headers().get("content-type").iterator().next());
    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          assertEquals(200, val.getStatusCode());
          assertNull(val.getErrorBody());
          assertNull(val.getRequestData());
          assertEquals(0, val.getResponseData().length);
          assertEquals("GET", val.getMethod());
        });
  }

  @Test
  @Order(1)
  void TestGetUserWithHeader() {
    GetHeader response = externalServiceClient.getUserWithHeader("txn123");
    assertEquals("response_with_header", response.getHeader());
    assertEquals("txn123", response.getTransactionId());
    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(200, val.getStatusCode());
            assertNull(val.getErrorBody());
            assertNotNull(val.getResponseData());
            assertEquals("GET", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  @Test
  @Order(2)
  void TestGetUserWithQueryParam() {
    GetQuery response = externalServiceClient.getUserWithQuery("txn123");
    assertEquals("response_with_query_param", response.getQuery());
    assertEquals("txn123", response.getTransactionId());

    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(200, val.getStatusCode());
            assertNull(val.getErrorBody());
            assertNotNull(val.getResponseData());
            assertEquals("GET", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  @Test
  @Order(3)
  void TestGetUserWithPathParam() {
    GetPath response = externalServiceClient.getUserWithPathParam("txn123");
    assertEquals("response_with_path_param", response.getPath());
    assertEquals("txn123", response.getTransactionId());
    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(200, val.getStatusCode());
            assertNull(val.getErrorBody());
            assertNotNull(val.getResponseData());
            assertEquals("GET", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  // ---------------- DELETE --------------------------

  @Test
  @Order(4)
  void TestDeleteUser() {
    DeleteUser response = externalServiceClient.deleteUsers();
    assertEquals("123", response.getUserId());
    assertEquals("txn123", response.getTransactionId());
    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(200, val.getStatusCode());
            assertNull(val.getErrorBody());
            assertNotNull(val.getResponseData());
            assertEquals("DELETE", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  @Test
  @Order(5)
  void TestDeleteUserWithHeader() {
    DeleteHeader response = externalServiceClient.deleteUserWithHeader("txn123");
    assertEquals("response_with_header", response.getHeader());
    assertEquals("txn123", response.getTransactionId());
    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(200, val.getStatusCode());
            assertNull(val.getErrorBody());
            assertNotNull(val.getResponseData());
            assertEquals("DELETE", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  @Test
  @Order(6)
  void TestDeleteUserWithQueryParam() {
    DeleteQuery response = externalServiceClient.deleteUserWithQuery("txn123");
    assertEquals("response_with_query_param", response.getQuery());
    assertEquals("txn123", response.getTransactionId());
    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(200, val.getStatusCode());
            assertNull(val.getErrorBody());
            assertNotNull(val.getResponseData());
            assertEquals("DELETE", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  @Test
  @Order(7)
  void TestDeleteUserWithPathParam() {
    DeletePath response = externalServiceClient.deleteUserWithPathParam("txn123");
    assertEquals("response_with_path_param", response.getPath());
    assertEquals("txn123", response.getTransactionId());
    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(200, val.getStatusCode());
            assertNull(val.getErrorBody());
            assertNotNull(val.getResponseData());
            assertEquals("DELETE", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
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
    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(200, val.getStatusCode());
            assertNull(val.getErrorBody());
            assertNotNull(val.getResponseData());
            assertEquals("HEAD", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  @Test
  @Order(10)
  void TestHEADUserWithQueryParam() {
    Response response = externalServiceClient.headUserWithQuery("txn123");
    String transactionId = response.headers().get("TRANSACTION_ID").iterator().next();
    assertEquals("txn456", transactionId);

    String method = response.headers().get("method").iterator().next();
    assertEquals("head", method);
    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(200, val.getStatusCode());
            assertNull(val.getErrorBody());
            assertNotNull(val.getResponseData());
            assertEquals("HEAD", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  @Test
  @Order(11)
  void TestHEADUserWithPathParam() {
    Response response = externalServiceClient.headUserWithPathParam("txn123");
    String transactionId = response.headers().get("TRANSACTION_ID").iterator().next();
    assertEquals("txn789", transactionId);

    String method = response.headers().get("method").iterator().next();
    assertEquals("head", method);
    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(200, val.getStatusCode());
            assertNull(val.getErrorBody());
            assertNotNull(val.getResponseData());
            assertEquals("HEAD", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  @Test
  @Order(12)
  void TestPOSTUserWithBody() {
    Response response = externalServiceClient.postUserWithBody(
        new PostUserBody("txn123", "post"));
    assertEquals(200, response.status());
    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(200, val.getStatusCode());
            assertNull(val.getErrorBody());
            assertNotNull(val.getResponseData());
            assertEquals("POST", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  @Test
  @Order(13)
  void TestPOSTUser() {
    Response response = externalServiceClient.postUser();
    assertEquals(200, response.status());
    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(200, val.getStatusCode());
            assertNull(val.getErrorBody());
            assertNotNull(val.getResponseData());
            assertEquals("POST", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  @Test
  @Order(14)
  void TestPOSTUserWithHeader() {
    Response response = externalServiceClient.postUserWithHeader("txn123");
    String transactionId = response.headers().get("TRANSACTION_ID").iterator().next();
    assertEquals("txn123", transactionId);

    String method = response.headers().get("method").iterator().next();
    assertEquals("head", method);
    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(200, val.getStatusCode());
            assertNull(val.getErrorBody());
            assertNotNull(val.getResponseData());
            assertEquals("POST", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  @Test
  @Order(15)
  void TestPOSTUserWithQueryParam() {
    Response response = externalServiceClient.postUserWithQuery("txn123");
    String transactionId = response.headers().get("TRANSACTION_ID").iterator().next();
    assertEquals("txn456", transactionId);

    String method = response.headers().get("method").iterator().next();
    assertEquals("head", method);
    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(200, val.getStatusCode());
            assertNull(val.getErrorBody());
            assertNotNull(val.getResponseData());
            assertEquals("POST", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  @Test
  @Order(16)
  void TestPOSTUserWithPathParam() {
    Response response = externalServiceClient.postUserWithPathParam("txn123");
    String transactionId = response.headers().get("TRANSACTION_ID").iterator().next();
    assertEquals("txn789", transactionId);

    String method = response.headers().get("method").iterator().next();
    assertEquals("head", method);
    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(200, val.getStatusCode());
            assertNull(val.getErrorBody());
            assertNotNull(val.getResponseData());
            assertEquals("POST", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  @Test
  @Order(17)
  void TestPOSTUserWithBodyWithoutResponseType() {
    PostUserBody response = externalServiceClient.postUserWithBodyWithoutResponseType(
        new PostUserBody("txn123", "post"));
    assertEquals("txn123", response.getTransactionId());
    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(200, val.getStatusCode());
            assertNull(val.getErrorBody());
            assertNotNull(val.getResponseData());
            assertEquals("POST", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  @Test
  @Order(18)
  void TestErrorWithGet() {
    Exception exception = assertThrows(Exception.class, () -> {
      externalServiceClient.getError(); // Simulate the API call
    });

    assertEquals(401, ((ApiException) exception).getStatusCode());
    assertTrue(exception.getMessage().contains("Unauthorized access"));

    this.getTestAuditDataStore().auditDataMap.forEach(
        (key, val) -> {
          try {
            assertEquals(401, val.getStatusCode());
            assertEquals("{\"error\":\"Unauthorized access\", \"code\":401}", val.getErrorBody());
            assertEquals("GET", val.getMethod());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }
}
