package com.phonepe.payments.fundtransfer.codec;

public interface RequestContextManager<T extends AuditContext> {

  T getContext();

  void setContext(T context);

}