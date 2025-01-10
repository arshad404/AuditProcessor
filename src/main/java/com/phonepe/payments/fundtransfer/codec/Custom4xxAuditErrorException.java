package com.phonepe.payments.fundtransfer.codec;

public class Custom4xxAuditErrorException extends Exception {
  Custom4xxAuditErrorException(String message, Exception e) {
    super(message, e);
  }
}
