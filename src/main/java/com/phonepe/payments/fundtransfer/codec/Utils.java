package com.phonepe.payments.fundtransfer.codec;

public class Utils {

  private Utils() {
    // Private Constructor to avoid initialization
  }

  public static Boolean isDecoderSkipped(String configKey) {
    try {
      // Feign configKey format: "feignClientClass#methodName([paramTypes])"
      int hashIndex = configKey.indexOf('#');
      if (hashIndex == -1) {
        return false;
      }

      String className = configKey.substring(0, hashIndex);
      String methodName = configKey.substring(hashIndex + 1, configKey.indexOf('('));

      // Check if the method's return type is Response.class
      return CodecRegistry.isAPIReturnOfResponseType(className, methodName);
    } catch (Exception e) {
      throw new CodecException("Exception while getting the method", e);
    }
  }
}
