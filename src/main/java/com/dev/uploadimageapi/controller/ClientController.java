package com.dev.uploadimageapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/")
@AllArgsConstructor
@CrossOrigin("*")
public class ClientController implements ErrorController {

  @GetMapping("/client")
  public String getClient() {
    return "forward:/index.html";
  }

}
