package com.example.demo.controllers;

import com.example.demo.annotations.Authorized;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/hello/{userId}")
    @Authorized(enabled = true)
    public ResponseEntity<?> sayHello(@PathVariable String userId){
        return ResponseEntity.ok("Hello user: " + userId);
    }
}
