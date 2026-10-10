package com.ariel.backendspring.backend_springboot_judefilms.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class SystemRenderController {

    @GetMapping("/ping") 
    public ResponseEntity<?> ping(){
        return ResponseEntity.ok("XD");
    }   
}
