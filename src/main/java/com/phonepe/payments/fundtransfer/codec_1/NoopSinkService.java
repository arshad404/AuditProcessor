package com.phonepe.payments.fundtransfer.codec_1;

public class NoopSinkService implements SinkService {

  @Override
  public void saveAuditData(String auditRequestContext, Object auditResponse) {
    // This is noop implementation, doing nothing
  }
}
