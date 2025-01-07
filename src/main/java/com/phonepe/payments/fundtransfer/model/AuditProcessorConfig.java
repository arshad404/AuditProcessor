package com.phonepe.payments.fundtransfer.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuditProcessorConfig {
  private boolean disableCompression;
  private boolean disableAuditing;

  AuditProcessorConfig(boolean disableCompression, boolean disableAuditing) {
    this.disableCompression = disableCompression;
    this.disableAuditing = disableAuditing;
  }

  public AuditProcessorConfig() {
    this.disableCompression = false;
    this.disableAuditing = false;
  }
}
