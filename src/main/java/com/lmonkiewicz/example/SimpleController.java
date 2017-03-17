package com.lmonkiewicz.example;

import com.lmonkiewicz.example.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by lmonkiewicz on 17.03.2017.
 */
@RestController
@RequestMapping("/simple")
public class SimpleController {

    @GetMapping("/hello")
    private String hello(){
        return "Hello World!";
    }

    @GetMapping("/hello/{name}")
    private String hello(@PathVariable("name") String name){
        return String.format("Hello %s!", name);
    }

    @GetMapping("/name")
    private ResponseEntity<?> queryPerson(@RequestParam("query") String query) {
        if ("Stefan".equals(query)) {
            return ResponseEntity.ok("Stefan Stefanowsky");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users")
    private ResponseEntity<?> createPerson(@Valid @RequestBody Person person) {
        return ResponseEntity.created(URI.create("/simple/users/1")).body("1");
    }
}
