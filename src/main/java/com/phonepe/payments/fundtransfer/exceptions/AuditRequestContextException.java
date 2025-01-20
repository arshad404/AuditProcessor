package com.phonepe.payments.fundtransfer.exceptions;

public class AuditRequestContextException extends Exception {

  public AuditRequestContextException(String message) {
    super(message);
  }

  public AuditRequestContextException(String message, Exception e) {
    super(message, e);
  }
}
