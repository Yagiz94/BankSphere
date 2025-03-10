package com.example.bankSphere.apitest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class MyLittleTestController {

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        System.out.println("Hello! This is a test endpoint.");
        return ResponseEntity.ok("Hello! This is a test endpoint.");
    }

    @PostMapping("/echo")
    public ResponseEntity<String> echo(@RequestBody String message) {
        System.out.println("You said: " + message);
        return ResponseEntity.ok("You said: " + message);
    }

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        System.out.println("API is working fine.");
        return ResponseEntity.ok("API is working fine.");
    }
}
