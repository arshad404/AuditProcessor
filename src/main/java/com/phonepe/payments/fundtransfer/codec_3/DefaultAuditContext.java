package com.phonepe.payments.fundtransfer.codec_3;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class DefaultAuditContext implements AuditContext {

  @Override
  public String getId() {
    return "DefaultAuditContextKey";
  }
}
