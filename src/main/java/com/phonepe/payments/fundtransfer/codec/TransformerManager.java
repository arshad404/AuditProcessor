package com.phonepe.payments.fundtransfer.codec;

import feign.RequestTemplate;
import feign.Response;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class TransformerManager {

  private static final Map<String, Method> requestTransformers = new HashMap<>();
  private static final Map<String, Method> responseTransformers = new HashMap<>();
  private static final Map<String, Method> loggerTransformers = new HashMap<>();
  private final Object transformerInstance;

  public TransformerManager(Class<?> transformerClass) {
    try {
      transformerInstance = transformerClass.getDeclaredConstructor().newInstance();
      for (Method method : transformerClass.getDeclaredMethods()) {
        processMethod(method);
      }
    } catch (ReflectiveOperationException e) {
      throw new CodecException("Error initializing TransformerManager", e);
    }
  }

  public Object applyRequestTransformation(String name, Object o, Type type,
      RequestTemplate requestTemplate) {
    return invokeTransformer(requestTransformers.get(name), o, type, requestTemplate);
  }

  public Object applyResponseTransformation(String name, Object o, Type type, Response response) {
    return invokeTransformer(responseTransformers.get(name), o, type, response);
  }

  public Response applyLoggerTransformation(String name, Response response) {
    return (Response) invokeTransformer(loggerTransformers.get(name), response);
  }

  private Object invokeTransformer(Method method, Object... args) {
    try {
      if (method != null) {
        return method.invoke(transformerInstance, args);
      }
    } catch (Exception e) {
      throw new CodecException("Error invoking transformer method: %s".formatted(method.getName()),
          e);
    }
    return args[0];
  }

  private void processMethod(Method method) {
    if (!method.isAnnotationPresent(AuditTransformer.class)) {
      return;
    }

    AuditTransformer annotation = method.getAnnotation(AuditTransformer.class);
    String returnType = method.getReturnType().getSimpleName();

    if ("Object".equals(returnType)) {
      processObjectMethod(method, annotation);
    } else if ("Response".equals(returnType)) {
      processResponseMethod(method, annotation);
    } else {
      log.error("Unexpected method return type: {}", returnType);
    }
  }

  private void processObjectMethod(Method method, AuditTransformer annotation) {
    if (method.getParameterTypes().length == 3) {
      if (method.getParameterTypes()[2] == RequestTemplate.class) {
        requestTransformers.put(annotation.name(), method);
      } else if (method.getParameterTypes()[2] == Response.class) {
        responseTransformers.put(annotation.name(), method);
      }
    }
  }

  private void processResponseMethod(Method method, AuditTransformer annotation) {
    if (method.getParameterTypes().length == 1 &&
        method.getParameterTypes()[0] == Response.class) {
      loggerTransformers.put(annotation.name(), method);
    }
  }
}

