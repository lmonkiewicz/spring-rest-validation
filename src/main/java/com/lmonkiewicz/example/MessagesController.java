package com.lmonkiewicz.example;

import com.lmonkiewicz.example.model.ErrorResponse;
import com.lmonkiewicz.example.model.Message;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by lmonkiewicz on 17.03.2017.
 */
@RestController
@RequestMapping("/messages")
public class MessagesController {

    @PostMapping
    public ResponseEntity<?> createMessage(@Valid @RequestBody Message message) {
        return ResponseEntity.created(URI.create("/messages/1")).body("1");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(MethodArgumentNotValidException exception) {

        String errorMsg = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(exception.getMessage());

        return ErrorResponse.builder().message(errorMsg).build();
    }


}
