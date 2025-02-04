package com.phonepe.payments.fundtransfer.codec;

import feign.Response;
import java.lang.reflect.Method;
import java.util.HashMap;

public class CodecRegistry {

  // Map to store method keys and whether their return type is Response.class
  private static final HashMap<String, Boolean> isApiReturnTypeResponse = new HashMap<>();

  private CodecRegistry() {
    // Prevent instantiation
  }

  // Initialize registry for a specific Feign client class
  public static void configure(Class<?> feignClass) {
    Method[] methods = feignClass.getMethods();

    for (Method method : methods) {
      // Generate a unique key for the method (using class name + method name)
      String methodKey = generateMethodKey(feignClass, method);

      // Check if the return type is Response.class
      boolean isResponseType = method.getReturnType().equals(Response.class);

      // Save this information in the map
      isApiReturnTypeResponse.put(methodKey, isResponseType);
    }
  }

  // Method to check if a given method's return type is Response.class
  public static boolean isAPIReturnOfResponseType(String className, String methodName) {
    String methodKey = className + "#" + methodName;

    // Check if the method key exists and its return type is Response.class
    return isApiReturnTypeResponse.getOrDefault(methodKey, false);
  }

  // Helper method to generate a unique key for the method
  private static String generateMethodKey(Class<?> feignClass, Method method) {
    // Create a key in the format "className#methodName"
    return feignClass.getSimpleName() + "#" + method.getName();
  }
}
