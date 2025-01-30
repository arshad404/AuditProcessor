package com.phonepe.payments.fundtransfer.codec_new;

public interface RequestContextManager<T extends AuditContext> {

  T getContext();

  void setContext(T context);

  void clearContext();
}