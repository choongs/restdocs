package com.tving.docs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestdocsController {

  @GetMapping("/user/restdocs/{id}")
  public Restdocs restdocs(@PathVariable String id, @RequestParam String method) {
    return Restdocs.builder().id(id).method(method)
        .name("choonghyun")
        .email("choonghyun83@gmail.com").build();
  }

  @PostMapping("/user/restdocs")
  public Restdocs restdocs(@RequestBody RestdocsForm restdocsForm) {
    return Restdocs.builder()
        .id(restdocsForm.getId())
        .name(restdocsForm.getName())
        .email(restdocsForm.getEmail())
        .method(restdocsForm.getMethod()).build();
  }
}
