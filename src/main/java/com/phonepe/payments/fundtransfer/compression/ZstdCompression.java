package com.phonepe.payments.fundtransfer.compression;

import com.github.luben.zstd.Zstd;
import com.phonepe.payments.fundtransfer.model.AuditProcessorConfig;

// Library is providing Zstd compressor
public class ZstdCompression implements Compression{

  private final AuditProcessorConfig auditProcessorConfig;

  public ZstdCompression(AuditProcessorConfig auditProcessorConfig) {
    this.auditProcessorConfig = auditProcessorConfig;
  }

  @Override
  public byte[] compress(byte[] data) {
    if(auditProcessorConfig.isDisableCompression()) return data;
    return Zstd.compress(data);
  }

  @Override
  public byte[] decompress(byte[] data) {
    return Zstd.decompress(data, data.length);
  }

  // Compression level for different request can be different as per the criticality of the API
  @Override
  public byte[] compress(byte[] data, int level) {
    if(auditProcessorConfig.isDisableCompression()) return data;
    if (level < 1 || level > 22) {
      throw new IllegalArgumentException("Compression level must be between 1 and 22");
    }
    return Zstd.compress(data, level);
  }
}
