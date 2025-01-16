package com.phonepe.payments.fundtransfer.codec_1;

import java.lang.reflect.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ContextStoreDao {
  private String id;
  private byte[] request;
  private Type type;
  private String method;
  private String requestPath;
}
