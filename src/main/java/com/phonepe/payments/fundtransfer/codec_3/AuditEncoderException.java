package com.phonepe.payments.fundtransfer.codec_3;

import feign.codec.EncodeException;

public class AuditEncoderException extends EncodeException {
  AuditEncoderException(String message) {
    super(message);
  }

  AuditEncoderException(String message, Exception e) {
    super(message, e);
  }
}
