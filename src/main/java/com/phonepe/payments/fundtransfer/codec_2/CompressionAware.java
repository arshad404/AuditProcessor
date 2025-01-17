package com.phonepe.payments.fundtransfer.codec_2;

public interface CompressionAware {

  byte[] compress(byte[] data);

}
