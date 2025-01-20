package com.phonepe.payments.fundtransfer.exceptions;

import feign.codec.EncodeException;

public class AuditEncoderException extends EncodeException {

  public AuditEncoderException(String message, Exception e) {
    super(message, e);
  }
}
