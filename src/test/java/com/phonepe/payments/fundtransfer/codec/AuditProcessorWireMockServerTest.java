package com.phonepe.payments.fundtransfer.codec;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.head;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AuditProcessorWireMockServerTest {

  protected WireMockServer wireMockServer;

  ObjectMapper objectMapper = new ObjectMapper();

  void setupWireMock() {
    wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(3000));
    wireMockServer.start();
    WireMock.configureFor("localhost", 3000);

//    /*
//    // For debugging the incoming request
//    wireMockServer.addMockServiceRequestListener((request, response) -> {
//      System.out.println("Received request with headers: " + request);
//      System.out.println("TRANSACTION_ID: " + request.getHeader("TRANSACTION_ID"));
//    });
//     */

    // Setup mock endpoints
    setupGetMock();
    setupDeleteMock();
    setupHeadMock();
    setupPostMock();
    setupPutMock();
    setupErrorScenarios();
    setupTransformerScenarios();
  }

  @AfterAll
  void teardown() {
    wireMockServer.stop();
  }

  private void setupGetMock() {
    // without anything
    stubFor(get(urlPathEqualTo("/api/users"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
        )
    );

    // with only header
    stubFor(get(urlPathEqualTo("/api/users/header"))
        .withHeader("TRANSACTION_ID", equalTo("txn123"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(
                "{\"header\":\"response_with_header\",\"transactionId\":\"txn123\",\"method\":\"get\"}")));

    // with only query param
    stubFor(get(urlPathEqualTo("/api/users/param"))
        .withQueryParam("transactionId", equalTo("txn123"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(
                "{\"query\":\"response_with_query_param\",\"transactionId\":\"txn123\",\"method\":\"get\"}")));

    // with only path param
    stubFor(get(urlPathMatching("/api/users/path/([a-zA-Z0-9]+)"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(
                "{\"path\":\"response_with_path_param\",\"transactionId\":\"txn123\",\"method\":\"get\"}")));
  }

  void setupDeleteMock() {
    // without anything
    stubFor(delete(urlPathEqualTo("/api/users"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"transactionId\":\"txn123\",\"userId\":\"123\"}")
        )
    );
    // with only header
    stubFor(delete(urlPathEqualTo("/api/users/header"))
        .withHeader("TRANSACTION_ID", equalTo("txn123"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(
                "{\"header\":\"response_with_header\",\"transactionId\":\"txn123\",\"method\":\"delete\"}")));

    // with only query param
    stubFor(delete(urlPathEqualTo("/api/users/param"))
        .withQueryParam("transactionId", equalTo("txn123"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(
                "{\"query\":\"response_with_query_param\",\"transactionId\":\"txn123\",\"method\":\"delete\"}")));

    // with only path param
    stubFor(delete(urlPathMatching("/api/users/path/([a-zA-Z0-9]+)"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(
                "{\"path\":\"response_with_path_param\",\"transactionId\":\"txn123\"},\"method\":\"delete\"")));
  }

  void setupHeadMock() {
    // without anything
    stubFor(head(urlPathEqualTo("/api/users"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
        )
    );

    // with only header
    stubFor(head(urlPathEqualTo("/api/users/header"))
        .withHeader("TRANSACTION_ID", equalTo("txn123"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("TRANSACTION_ID", "txn123")
            .withHeader("method", "head")));

//    // with only query param
    stubFor(head(urlPathEqualTo("/api/users/param"))
        .withQueryParam("transactionId", equalTo("txn123"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("TRANSACTION_ID", "txn456")
            .withHeader("method", "head")));

    // with only path param
    stubFor(head(urlPathMatching("/api/users/path/([a-zA-Z0-9]+)"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("TRANSACTION_ID", "txn789")
            .withHeader("method", "head")));

  }

  void setupPostMock() {
    stubFor(post(urlPathEqualTo("/api/users"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("TRANSACTION_ID", "txn123")
            .withHeader("method", "head")
            .withBody(
                "{\"transactionId\":\"txn123\", \"method\":\"post\"}")));

    // With request body
    stubFor(post(urlPathEqualTo("/api/users/body"))
        .withRequestBody(equalToJson("{\"transactionId\":\"txn123\", \"method\":\"post\"}"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("TRANSACTION_ID", "txn123")
            .withHeader("method", "head")
            .withBody(
                "{\"transactionId\":\"txn123\", \"method\":\"post\"}")));

    // with only header
    stubFor(post(urlPathEqualTo("/api/users/header"))
        .withHeader("TRANSACTION_ID", equalTo("txn123"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("TRANSACTION_ID", "txn123")
            .withHeader("method", "head")
            .withBody(
                "{\"transactionId\":\"txn123\", \"method\":\"post\"}")));

    // with only query param
    stubFor(post(urlPathEqualTo("/api/users/param"))
        .withQueryParam("transactionId", equalTo("txn123"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("TRANSACTION_ID", "txn456")
            .withHeader("method", "head")
            .withBody(
                "{\"transactionId\":\"txn123\", \"method\":\"post\"}")));

    // with only path param
    stubFor(post(urlPathMatching("/api/users/path/([a-zA-Z0-9]+)"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("TRANSACTION_ID", "txn789")
            .withHeader("method", "head")
            .withBody(
                "{\"transactionId\":\"txn123\", \"method\":\"post\"}")));

    // with only path param
    stubFor(post(urlPathMatching("/api/users/body/without/response/type"))
        .withRequestBody(equalToJson("{\"transactionId\":\"txn123\", \"method\":\"post\"}"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("TRANSACTION_ID", "txn789")
            .withHeader("method", "head")
            .withBody(
                "{\"transactionId\":\"txn123\", \"method\":\"post\"}")));
  }

  void setupErrorScenarios() {
    stubFor(get(urlPathEqualTo("/api/error/4xx"))
        .willReturn(aResponse()
            .withStatus(401)  // Unauthorized error
            .withHeader("Content-Type", "application/json")
            .withBody("{\"error\":\"Unauthorized access\", \"code\":401}")
        ));

    stubFor(post(urlPathEqualTo("/api/error/4xx"))
        .willReturn(aResponse()
            .withStatus(401)  // Unauthorized error
            .withHeader("Content-Type", "application/json")
            .withBody("{\"error\":\"Unauthorized access\", \"code\":401}")
        ));

    stubFor(get(urlPathEqualTo("/api/error/5xx"))
        .willReturn(aResponse()
            .withStatus(501)  // Unauthorized error
            .withHeader("Content-Type", "application/json")
            .withBody("{\"error\":\"Server Down\", \"code\":501}")
        ));

    stubFor(post(urlPathEqualTo("/api/error/5xx"))
        .willReturn(aResponse()
            .withStatus(501)  // Unauthorized error
            .withHeader("Content-Type", "application/json")
            .withBody("{\"error\":\"Server Down\", \"code\":501}")
        ));

    stubFor(post(urlPathEqualTo("/api/error/5xx/body"))
        .withRequestBody(equalToJson("{\"transactionId\":\"txn123\", \"method\":\"post\"}"))
        .willReturn(aResponse()
            .withStatus(501)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"error\":\"Server Down\", \"code\":501}")
        ));

    stubFor(post(urlPathEqualTo("/api/error/4xx/body"))
        .withRequestBody(equalToJson("{\"transactionId\":\"txn123\", \"method\":\"post\"}"))
        .willReturn(aResponse()
            .withStatus(403)  // Unauthorized error
            .withHeader("Content-Type", "application/json")
            .withBody("{\"error\":\"Unauthorized access\", \"code\":403}")
        ));
  }

  void setupPutMock() {
    stubFor(put(urlPathEqualTo("/api/users"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("TRANSACTION_ID", "txn123")
            .withHeader("method", "put")
            .withBody(
                "{\"transactionId\":\"txn123\", \"method\":\"put\"}")));

    // With request body
    stubFor(put(urlPathEqualTo("/api/users/body"))
        .withRequestBody(equalToJson("{\"transactionId\":\"txn123\", \"method\":\"put\"}"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("TRANSACTION_ID", "txn123")
            .withHeader("method", "put")
            .withBody(
                "{\"transactionId\":\"txn123\", \"method\":\"put\"}")));

    // with only header
    stubFor(put(urlPathEqualTo("/api/users/header"))
        .withHeader("TRANSACTION_ID", equalTo("txn123"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("TRANSACTION_ID", "txn123")
            .withHeader("method", "put")
            .withBody(
                "{\"transactionId\":\"txn123\", \"method\":\"put\"}")));

    // with only query param
    stubFor(put(urlPathEqualTo("/api/users/param"))
        .withQueryParam("transactionId", equalTo("txn123"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("TRANSACTION_ID", "txn456")
            .withHeader("method", "put")
            .withBody(
                "{\"transactionId\":\"txn123\", \"method\":\"put\"}")));

    // with only path param
    stubFor(put(urlPathMatching("/api/users/path/([a-zA-Z0-9]+)"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("TRANSACTION_ID", "txn789")
            .withHeader("method", "put")
            .withBody(
                "{\"transactionId\":\"txn123\", \"method\":\"put\"}")));

    // with only path param
    stubFor(put(urlPathMatching("/api/users/body/without/response/type"))
        .withRequestBody(equalToJson("{\"transactionId\":\"txn123\", \"method\":\"put\"}"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("TRANSACTION_ID", "txn789")
            .withHeader("method", "put")
            .withBody(
                "{\"transactionId\":\"txn123\", \"method\":\"put\"}")));
  }

  void setupTransformerScenarios() {
    // With request body
    stubFor(post(urlPathEqualTo("/api/users/body/transform"))
        .withRequestBody(equalToJson("{\"transactionId\":\"txn123\", \"method\":\"post\"}"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("TRANSACTION_ID", "txn123")
            .withHeader("method", "head")
            .withBody(
                "{\"transactionId\":\"txn123\", \"method\":\"post\"}")));
  }
}