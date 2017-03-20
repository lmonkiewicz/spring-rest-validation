package com.lmonkiewicz.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by lmonkiewicz on 20.03.2017.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @NotNull
    private String title;
    @NotNull
    private String message;
}
