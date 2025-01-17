package com.phonepe.payments.fundtransfer.codec_3;

import java.lang.reflect.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ContextStore implements AuditContext {
  private String id;
  private Object requestObject;
  private byte[] request;
  private Type type;
  private String method;
  private String requestPath;
}
