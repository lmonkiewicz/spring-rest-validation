package com.lmonkiewicz.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmonkiewicz.example.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lmonkiewicz on 17.03.2017.
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class WebUsersTests {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void POST_OnUsersWithNoBodyShouldReturnBadRequest400() throws Exception {
        mockMvc.perform(
                post("/users"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void POST_OnUsersWithFullDataShouldReturnIdAndCreatedStatus() throws Exception {
        mockMvc.perform(
                post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(
                        Person.builder().firstName("Stefan").lastName("Stefanowsky").age(35).build()
                    ))
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/users/1"))
                .andExpect(content().string("1"));
    }

    @Test
    public void POST_OnUsersWithNoFirstNameShouldReturnBadRequest400() throws Exception {
        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(
                                Person.builder().lastName("Stefanowsky").age(22).build()
                        ))
                )
                .andExpect(status().isBadRequest());

    }

    private String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
}
