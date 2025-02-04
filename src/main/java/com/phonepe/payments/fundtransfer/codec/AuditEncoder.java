package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import java.lang.reflect.Type;
import java.util.ArrayList;
import lombok.Getter;

@Getter
public abstract class AuditEncoder<A extends AuditContext> implements Encoder {

  private final ObjectMapper objectMapper;
  private final RequestContextManager<A> requestContextManager;
  private final Encoder encoder;
  private final TransformerManager transformerManager;

  protected AuditEncoder(ObjectMapper objectMapper, RequestContextManager<A> requestContextManager,
      Encoder encoder, ArrayList<Transformer> transformers) {
    this.objectMapper = objectMapper;
    this.requestContextManager = requestContextManager;
    this.encoder = encoder;
    transformerManager = new TransformerManager();
    transformers.forEach(this.transformerManager::addRequestTransformer);
  }

  // No transformer
  protected AuditEncoder(ObjectMapper objectMapper, RequestContextManager<A> requestContextManager,
      Encoder encoder) {
    // Calls primary constructor
    this(objectMapper, requestContextManager, encoder, new ArrayList<>());
  }


  @Override
  public void encode(Object object, Type bodyType, RequestTemplate template)
      throws EncodeException {
    try {
      // Transform
      var transformedObject = this.transformerManager.applyRequestTransformers(object, bodyType,
          template);

      // Update the context
      A auditContext = setAuditContext(object, transformedObject, bodyType, template);
      requestContextManager.setContext(auditContext);

      // Encode the request
      var transformedType = this.objectMapper.getTypeFactory()
          .findClass(transformedObject.getClass().getName());
      encoder.encode(transformedObject, transformedType, template);
    } catch (ClassNotFoundException e) {
      throw new CodecException("Failed to encode the audit request", e);
    }

  }

  // dev can store the real object or the transformed object
  protected abstract A setAuditContext(Object object, Object transformedObject, Type bodyType,
      RequestTemplate template);
}
