package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
//import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthController {
//    @Autowired
//    private SeaBattleGame userService;

    @GetMapping(value = "login")
    public void Login (){//(@Valid @RequestBody AuthorisationModel authModel){

    }
}
