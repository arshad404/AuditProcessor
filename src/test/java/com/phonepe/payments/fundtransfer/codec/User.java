package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
  // Getters and setters
  @JsonProperty("name")
  private String name;

  @JsonProperty("age")
  private int age;

}

