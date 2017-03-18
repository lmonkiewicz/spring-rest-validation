package com.lmonkiewicz.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by lmonkiewicz on 17.03.2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Integer age;
}
