package com.phonepe.payments.fundtransfer.codec;

import com.phonepe.payments.fundtransfer.util.SerializationUtil;
import feign.RequestTemplate;
import feign.codec.Encoder;
import java.io.IOException;
import java.lang.reflect.Type;

/*
    * 1. Fetch the data that need to be logged it, default it is request body
    * 2. compress the data that is fetched for the request
    // Default: it will not compress and return the bytes that are received in the request
    // @param: byte[] - each compressed algorithm takes bytes as input and nothing else, so passing other parameters would be waste
    * 3. Encrypt the compressed data
    * 4. Encode the request that is going on WIRE
    * 5. Sink the compressed encrypted data
*/
public abstract class AuditEncoder implements Encoder {

  private final Encoder defaultEncoder = DefaultCodec.getEncoder();

  @Override
  public void encode(Object o, Type type, RequestTemplate requestTemplate) {
    try {
      // 1. Fetch the data that need to be logged it, default it is request body
      byte[] requestToAudit = fetchAuditData(o, type, requestTemplate);

      // 2. compress the data that is fetched for the request
      // Default: it will not compress and return the bytes that are received in the request
      // @param: byte[] - each compressed algorithm takes bytes as input and nothing else, so passing other parameters would be waste
      byte[] compressedData = compress(requestToAudit);

      // 3. Encrypt the compressed data
      byte[] encryptedData = encrypt(compressedData);

      // 4. Encode the request that is going on WIRE
      encodeAuditRequest(o, type, requestTemplate);

      // 5. Sink the compressed encrypted data
      sinkData(o, type, requestTemplate, encryptedData);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected byte[] fetchAuditData(Object o, Type type, RequestTemplate requestTemplate) throws IOException {
    return SerializationUtil.toByteArray(o);
  }

  protected byte[] compress(byte[] data) {
    return data;
  }

  protected byte[] encrypt(byte[] data) {
    return data;
  }

  protected void encodeAuditRequest(Object o, Type type, RequestTemplate requestTemplate) {
    this.defaultEncoder.encode(o, type, requestTemplate);
  }

  protected abstract void sinkData(Object o, Type type, RequestTemplate requestTemplate, byte[] data);
}

