package com.phonepe.payments.fundtransfer.codec;

import java.io.IOException;

public class TestServerManager {
  private Process serverProcess;
  private String path;

  public TestServerManager(String path) {
    this.path = path;
  }

  public void startServer() throws IOException {
    serverProcess = Runtime.getRuntime().exec(path);
  }

  public void stopServer() throws IOException, InterruptedException {
    if (serverProcess != null) {
      ProcessBuilder processBuilder = new ProcessBuilder(path);
      Process stopProcess = processBuilder.start();
      stopProcess.waitFor();
      serverProcess.destroy();
      System.out.println("Test server stopped...");
    }
  }
}
