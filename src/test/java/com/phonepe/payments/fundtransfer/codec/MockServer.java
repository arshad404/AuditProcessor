package com.phonepe.payments.fundtransfer.codec;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MockServer {
  private static HttpServer server;
  private static final List<User> users = new ArrayList<>();
  private static final Lock lock = new ReentrantLock();
  private static final ObjectMapper objectMapper = new ObjectMapper();

  // User model
  static class User {
    public String name;
    public int age;

    public User() {}
    public User(String name, int age) {
      this.name = name;
      this.age = age;
    }
  }

  public static void start() throws IOException {
    server = HttpServer.create(new InetSocketAddress(3000), 0);

    server.createContext("/users", exchange -> {
      if ("POST".equals(exchange.getRequestMethod())) {
        createUser(exchange);
      } else if ("GET".equals(exchange.getRequestMethod())) {
        getUsers(exchange);
      } else {
        sendResponse(exchange, 405, "Method Not Allowed");
      }
    });

    server.createContext("/error", exchange -> sendResponse(exchange, 500, "Internal Server Error"));

    System.out.println("Mock server started on http://localhost:3000/");
    server.start();
  }

  public static void stop() {
    if (server != null) {
      server.stop(0);
      System.out.println("Mock server stopped.");
    }
  }

  private static void createUser(HttpExchange exchange) throws IOException {
    try {
      User newUser = objectMapper.readValue(exchange.getRequestBody(), User.class);
      lock.lock();
      try {
        users.add(newUser);
      } finally {
        lock.unlock();
      }
      sendResponse(exchange, 200, objectMapper.writeValueAsString(newUser));
    } catch (Exception e) {
      sendResponse(exchange, 400, "Invalid JSON: " + e.getMessage());
    }
  }

  private static void getUsers(HttpExchange exchange) throws IOException {
    lock.lock();
    try {
      sendResponse(exchange, 200, objectMapper.writeValueAsString(users));
    } finally {
      lock.unlock();
    }
  }

  private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
    exchange.sendResponseHeaders(statusCode, response.getBytes().length);
    try (OutputStream os = exchange.getResponseBody()) {
      os.write(response.getBytes());
    }
  }
}
