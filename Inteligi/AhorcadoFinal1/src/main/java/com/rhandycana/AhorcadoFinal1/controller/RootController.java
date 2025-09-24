package com.rhandycana.AhorcadoFinal1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public String rootMessage() {
        return "Debes usar /api/usuarios o /api/palabras para acceder a la API.";
    }
}
