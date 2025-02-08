package com.phonepe.payments.fundtransfer.codec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequestTransformer {

  String methodName();
}
