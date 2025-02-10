package com.phonepe.payments.fundtransfer.codec;

import feign.RequestTemplate;
import feign.Response;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

@Getter
@Setter
public class TransformerManager<T> {

  private final Class<T> client;
  private final Map<String, Transformer> transformers = new HashMap<>();

  public TransformerManager(Class<T> client) {
    this.client = client;

    try {
      // Initialize transformers as before
      String packageName = client.getPackageName();
      Reflections reflections = new Reflections(
          new ConfigurationBuilder()
              .forPackage(packageName)
              .addScanners(Scanners.SubTypes)
      );

      var classes = reflections.getSubTypesOf(Transformer.class);

      for (Class<? extends Transformer> transformerClass : classes) {
        Transformer transformer = transformerClass.getDeclaredConstructor().newInstance();
        transformers.putIfAbsent(transformer.value(), transformer);
      }
    } catch (Exception e) {
      throw new CodecException("Exception occurred while initializing TransformerManager", e);
    }
  }

  // Retrieve transformer for request transformation
  public Object applyRequestTransformers(Object input, Type type, RequestTemplate requestTemplate) {
    String transformerName = getTransformerName(requestTemplate.methodMetadata().method());
    if (transformerName != null && transformers.containsKey(transformerName)) {
      return transformers.get(transformerName).transformRequest(input, type, requestTemplate);
    }
    return input;
  }

  // Retrieve transformer for response transformation
  public Object applyResponseTransformers(Object input, Type type, Response response) {
    String transformerName = getTransformerName(
        response.request().requestTemplate().methodMetadata().method());
    if (transformerName != null && transformers.containsKey(transformerName)) {
      return transformers.get(transformerName).transformResponse(input, type, response);
    }
    return input;
  }

  // Retrieve transformer for response transformation
  public Response applyResponseTransformerInLogger(Response response) {
    String transformerName = getTransformerName(
        response.request().requestTemplate().methodMetadata().method());
    if (transformerName != null && transformers.containsKey(transformerName)) {
      return transformers.get(transformerName).transformResponseInLogger(response);
    }
    return response;
  }


  // Helper to extract the transformer name from @AuditTransformer annotation
  private String getTransformerName(Method method) {
    AuditTransformer annotation = method.getAnnotation(AuditTransformer.class);
    return (annotation != null) ? annotation.value() : null;
  }
}
