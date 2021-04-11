package com.tving.docs;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Restdocs {

  private String id;
  private String name;
  private String email;
  private String method;
}
