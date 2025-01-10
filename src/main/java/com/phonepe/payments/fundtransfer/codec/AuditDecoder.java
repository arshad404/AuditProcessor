package com.phonepe.payments.fundtransfer.codec;

import com.phonepe.payments.fundtransfer.util.SerializationUtil;
import feign.Response;
import feign.codec.Decoder;
import java.io.IOException;
import java.lang.reflect.Type;

public abstract class AuditDecoder implements Decoder {
  private final Decoder decoder = DefaultCodec.getDecoder();

  /*
  decode:
   */
  @Override
  public Object decode(Response response, Type type) {
    try {
      // 1. Decode the received response
      Object responseObject = decodeAuditResponse(response, type);

      // 1. transform the object into byte array which needs to be sink
      // For example in FT,
      //   - take the responseObject
      //   - get the request url and based on the request url, decrypt the response
      //   - send bank the byte[] of the readable object
      byte[] responseBytes = transform(response, type, responseObject);

      // 3. compress the data that is fetched for the request
      // Default: it will not compress and return the bytes that are received in the request
      // @param: byte[] - each compressed algorithm takes bytes as input and nothing else, so passing other parameters would be waste
      byte[] compressedData = compress(responseBytes);

      // 4. Encrypt the compressed data
      byte[] encryptedData = encrypt(compressedData);

      // 5. sink the data to the database
      sinkData(response, type, encryptedData);

      return responseObject;
    } catch (Exception e) {
      return new RuntimeException(e);
    }
  }

  protected Object decodeAuditResponse(Response response, Type type) throws IOException {
    return this.decoder.decode(response, type);
  }

  protected byte[] compress(byte[] data) {
    return data;
  }

  protected byte[] encrypt(byte[] data) {
    return data;
  }

  protected byte[] transform(Response response, Type type, Object object) throws IOException {
    return SerializationUtil.toByteArray(object);
  }

  protected abstract void sinkData(Response response, Type type, byte[] data);
}

