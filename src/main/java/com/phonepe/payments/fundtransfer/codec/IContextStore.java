package com.phonepe.payments.fundtransfer.codec;

public interface IContextStore<T extends IAuditContext> {
  /**
   * Stores a value in the context with a specified key.
   *
   * @param value the value to store
   * @throws Exception if an error occurs during storage (e.g., serialization failure)
   */
  void setContext(T value) throws Exception;

  /**
   * Retrieves a value from the context using the specified key.
   *
   * @return the deserialized value, or null if the key does not exist in the context
   * @throws Exception if an error occurs during retrieval (e.g., deserialization failure)
   */
  T getContext() throws Exception;
}