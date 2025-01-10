package com.phonepe.payments.fundtransfer.database;

import feign.RequestTemplate;
import feign.Response;

public interface DataSink {

  void save(RequestTemplate requestTemplate, byte[] data);
  void update(Response response, String key, byte[] data);
}

