package com.example.secureapione.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Anthony Ikeda <anthony.ikeda@gmail.com> on 3/06/2018.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("Message received!");
    }
}
