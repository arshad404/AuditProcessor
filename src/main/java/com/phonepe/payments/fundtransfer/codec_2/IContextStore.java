package com.phonepe.payments.fundtransfer.codec_2;

public interface IContextStore<T> {
  /**
   * Stores a value in the context with a specified key.
   *
   * @param key the key to identify the context
   * @param value the value to store
   * @throws Exception if an error occurs during storage (e.g., serialization failure)
   */
  void setContext(String key, T value) throws Exception;

  /**
   * Retrieves a value from the context using the specified key.
   *
   * @param key the key to identify the context
   * @param valueType the class type to deserialize the value into
   * @return the deserialized value, or null if the key does not exist in the context
   * @throws Exception if an error occurs during retrieval (e.g., deserialization failure)
   */
  T getContext(String key, Class<T> valueType) throws Exception;
}
