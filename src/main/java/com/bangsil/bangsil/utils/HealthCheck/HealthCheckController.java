package com.bangsil.bangsil.utils.HealthCheck;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/api/v1/")
    public ResponseEntity<Object> healthCheck() {
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }
}