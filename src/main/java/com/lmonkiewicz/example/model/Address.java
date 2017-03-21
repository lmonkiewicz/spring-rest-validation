package com.lmonkiewicz.example.model;

import com.lmonkiewicz.example.validation.IsCorrectAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lmonkiewicz on 20.03.2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IsCorrectAddress
public class Address {
    private boolean company;
    private String name;
    private String street;
    private String taxId;
}
