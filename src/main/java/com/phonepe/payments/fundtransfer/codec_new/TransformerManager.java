package com.phonepe.payments.fundtransfer.codec_new;

import feign.RequestTemplate;
import feign.Response;
import java.lang.reflect.Type;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransformerManager {

  private ArrayList<Transformer> requestTransformers = new ArrayList<>();
  private ArrayList<Transformer> responseTransformers = new ArrayList<>();


  public void addRequestTransformer(Transformer transformer) {
    this.requestTransformers.add(transformer);
  }

  public void addResponseTransformer(Transformer transformer) {
    this.responseTransformers.add(transformer);
  }

  // Applying the request transformation
  public Object applyRequestTransformers(Object input, Type type, RequestTemplate requestTemplate) {
    Object transformedObject = input;

    for (Transformer transformer : requestTransformers) {
      transformedObject = transformer.transformRequest(transformedObject, type, requestTemplate);
    }

    return transformedObject;
  }

  // Applying the response transformation
  public Object applyResponseTransformers(Object input, Type type, Response response) {
    Object transformedObject = input;

    for (Transformer transformer : requestTransformers) {
      transformedObject = transformer.transformResponse(transformedObject, type, response);
    }

    return transformedObject;
  }
}
