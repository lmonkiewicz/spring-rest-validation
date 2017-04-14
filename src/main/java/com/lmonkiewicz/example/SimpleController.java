package com.lmonkiewicz.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private String hello(@PathVariable(value = "name", required = true) String name){
        return String.format("Hello %s!", name);
    }

    @GetMapping("/name")
    private ResponseEntity<?> queryPerson(@RequestParam(value = "query", required = false) String query) {
        if ("Stefan".equals(query)) {
            return ResponseEntity.ok("Stefan Stefanowsky");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
