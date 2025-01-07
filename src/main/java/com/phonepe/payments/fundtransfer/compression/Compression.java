package com.phonepe.payments.fundtransfer.compression;

public interface Compression {

  byte[] compress(byte[] data);

  byte[] decompress(byte[] data);

  default byte[] compress(byte[] data, int level) {
    return this.compress(data);
  }
}
