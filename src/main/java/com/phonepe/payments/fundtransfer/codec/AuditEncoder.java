package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import java.lang.reflect.Type;
import lombok.Getter;

@Getter
public abstract class AuditEncoder<A extends AuditContext> implements Encoder {

  private final ObjectMapper objectMapper;
  private final RequestContextManager<A> requestContextManager;
  private final Encoder delegate;
  private final TransformerManager transformerManager;

  protected AuditEncoder(ObjectMapper objectMapper, RequestContextManager<A> requestContextManager,
      Encoder delegate, Class<?> client) {
    this.objectMapper = objectMapper;
    this.requestContextManager = requestContextManager;
    this.delegate = delegate;
    transformerManager = new TransformerManager(client);
  }

  @Override
  public void encode(Object object, Type bodyType, RequestTemplate template)
      throws EncodeException {
    try {

      // Retrieve transformer name
      String transformerName = Utils.getTransformerName(template);

      if (transformerName != null) {
        object = transformerManager.applyRequestTransformation(transformerName, object, bodyType,
            template);
      }

      // Update the context
      A auditContext = setAuditContext(object, bodyType, template);
      requestContextManager.setContext(auditContext);

      // Encode the request
      var transformedType = this.objectMapper.getTypeFactory()
          .findClass(object.getClass().getName());
      delegate.encode(object, transformedType, template);
    } catch (ClassNotFoundException e) {
      throw new CodecException("Failed to encode the audit request", e);
    }

  }

  // dev can store the real object or the transformed object
  protected abstract A setAuditContext(Object object, Type bodyType, RequestTemplate template);
}
