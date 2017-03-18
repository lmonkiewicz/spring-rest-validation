package com.lmonkiewicz.example.model;

import com.lmonkiewicz.example.validation.InRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Created by lmonkiewicz on 17.03.2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotNull(groups = Existing.class)
    @Null(groups = New.class)
    private Long id;

    @NotNull(
        message = "First name is required",
        groups = {Existing.class, New.class}
    )
    private String firstName;

    @NotNull(groups = {Existing.class, New.class})
    private String lastName;

    @NotNull(groups = {Existing.class, New.class})
    @InRange(
        min=18,
        message = "User must be at least 18 years old",
        groups = {Existing.class, New.class}
    )
    private Integer age;




    public interface Existing {
    }

    public interface New {
    }
}
