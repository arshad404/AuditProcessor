package com.phonepe.payments.fundtransfer.service;

import com.phonepe.payments.fundtransfer.compression.ZstdCompression;
import com.phonepe.payments.fundtransfer.database.DataSink;
import com.phonepe.payments.fundtransfer.model.AuditProcessorConfig;
import feign.RequestTemplate;
import feign.Response;
import java.util.Arrays;

public class DataSinkImpl implements DataSink {

  ZstdCompression zstdCompression = new ZstdCompression(AuditProcessorConfig.builder().build());

  @Override
  public void save(RequestTemplate requestTemplate, byte[] data) {
    System.out.println("Saved File Successfully: " + Arrays.toString(zstdCompression.decompress(data)));
  }

  @Override
  public void update(Response response, String key, byte[] data) {
    System.out.println("updated File Successfully: key:: " + key + " " + Arrays.toString(data));
  }
}
