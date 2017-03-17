package com.lmonkiewicz.example;

import com.lmonkiewicz.example.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by lmonkiewicz on 17.03.2017.
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @PostMapping
    private ResponseEntity<?> createPerson(@Valid @RequestBody Person person) {
        return ResponseEntity.created(URI.create("/users/1")).body("1");
    }
}
