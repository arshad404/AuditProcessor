package com.phonepe.payments.fundtransfer.codec;

public class Custom5xxAuditErrorException extends Exception {
  Custom5xxAuditErrorException(String message, Exception e) {
    super(message, e);
  }
}
