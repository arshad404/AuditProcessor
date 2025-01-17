package com.phonepe.payments.fundtransfer.codec;

import feign.codec.EncodeException;

public class AuditEncoderException extends EncodeException {

  AuditEncoderException(String message, Exception e) {
    super(message, e);
  }
}
