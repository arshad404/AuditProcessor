package com.phonepe.payments.fundtransfer.codec;

public class CodecException extends RuntimeException {

  CodecException(String message) {
    super(message);
  }

  CodecException(String message, Exception e) {
    super(message, e);
  }

}
