package com.phonepe.payments.fundtransfer.codec;

import feign.RequestTemplate;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

  public static String getTransformerName(RequestTemplate requestTemplate) {
    return Utils.getAuditTransformerName(
        MessageFormat.format("{0}#{1}",
            requestTemplate.feignTarget().type().getName(),
            requestTemplate.methodMetadata().method().getName()));
  }

  private static String getAuditTransformerName(String configKey) {
    try {
      // Extract class name and method name from configKey
      int hashIndex = configKey.indexOf('#');
      int paramIndex = configKey.indexOf('(');

      // Handle missing parameter types
      String methodName = (paramIndex != -1)
          ? configKey.substring(hashIndex + 1, paramIndex)
          : configKey.substring(hashIndex + 1);

      String className = configKey.substring(0, hashIndex);

      // Load Feign Client class dynamically
      Class<?> feignClientClass = Class.forName(className);

      // Find the method with matching name and annotation
      for (Method method : feignClientClass.getDeclaredMethods()) {
        if (method.getName().equals(methodName) && method.isAnnotationPresent(
            AuditTransformer.class)) {
          return method.getAnnotation(AuditTransformer.class).name();
        }
      }
    } catch (ClassNotFoundException e) {
      log.error("Feign Client class not found: %s".formatted(e.getMessage()));
    } catch (Exception e) {
      log.error("Error processing Feign Client method: %s".formatted(e.getMessage()));
    }
    return null; // Return null if no annotation found
  }


}
