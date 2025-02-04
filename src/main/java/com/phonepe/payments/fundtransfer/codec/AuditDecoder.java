package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import lombok.Getter;

@Getter
public abstract class AuditDecoder<A extends AuditContext> implements Decoder {

  private final ObjectMapper objectMapper;
  private final RequestContextManager<A> requestContextManager;
  private final Decoder decoder;
  private final TransformerManager transformerManager;
  private final AuditDataStore<A> auditDataStore;

  protected AuditDecoder(RequestContextManager<A> requestContextManager,
      Decoder decoder, ArrayList<Transformer> transformers, ObjectMapper objectMapper,
      AuditDataStore<A> auditDataStore) {
    this.requestContextManager = requestContextManager;
    this.decoder = decoder;
    this.objectMapper = objectMapper;
    this.auditDataStore = auditDataStore;
    transformerManager = new TransformerManager();
    transformers.forEach(this.transformerManager::addRequestTransformer);

  }

  protected AuditDecoder(RequestContextManager<A> requestContextManager,
      Decoder decoder, AuditDataStore<A> auditDataStore, ObjectMapper objectMapper) {
    this(requestContextManager, decoder, new ArrayList<>(), objectMapper, auditDataStore);
  }


  @Override
  public Object decode(Response response, Type type) throws FeignException {
    try {
      // Decode
      var decodedObject = this.decoder.decode(response, type);

      // Transform
      var transformedObject = this.transformerManager.applyResponseTransformers(decodedObject, type,
          response);

      // update the context
      var updatedContext = this.setAuditContext(response, type, transformedObject);
      this.requestContextManager.setContext(updatedContext);

      // save the data in the response
      auditDataStore.saveAuditData(this.requestContextManager.getContext());
      
      return transformedObject;
    } catch (IOException e) {
      throw new CodecException("Failed to decode the audit response", e);
    }
  }

  // dev can store the real object or the transformed object
  protected abstract A setAuditContext(Response response, Type type, Object decodedObject);
}
