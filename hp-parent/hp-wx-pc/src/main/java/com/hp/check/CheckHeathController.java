package com.hp.check;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class CheckHeathController {
  @RequestMapping("/health_check")
  public String  healthcheck() {
  
      return "OK";
  }   
}