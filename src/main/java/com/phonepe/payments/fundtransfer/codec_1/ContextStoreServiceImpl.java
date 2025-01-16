package com.phonepe.payments.fundtransfer.codec_1;


import org.slf4j.MDC;

public class ContextStoreServiceImpl implements ContextStoreService {

  @Override
  public void setContext(String key, String val) {
    MDC.put(key, val);
  }

  @Override
  public String getContext(String key) {
    return MDC.get(key);
  }
}
