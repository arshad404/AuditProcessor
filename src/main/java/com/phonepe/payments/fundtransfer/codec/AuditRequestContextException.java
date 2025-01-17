package com.phonepe.payments.fundtransfer.codec;

public class AuditRequestContextException extends Exception {

  AuditRequestContextException(String message) {
    super(message);
  }

  public AuditRequestContextException(String message, Exception e) {
    super(message, e);
  }
}
