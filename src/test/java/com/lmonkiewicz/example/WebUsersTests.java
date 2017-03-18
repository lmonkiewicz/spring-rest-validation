package com.lmonkiewicz.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmonkiewicz.example.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                        User.builder().firstName("Stefan").lastName("Stefanowsky").age(35).build()
                    ))
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/users/1"))
                .andExpect(content().string("1"));
    }

    @Test
    public void POST_OnUsersWithSetIdShouldReteurnBadRequest400() throws Exception {
        mockMvc.perform(
                post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(
                            User.builder().id(1L).firstName("Stefan").lastName("Stefanowsky").age(35).build()
                    ))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void POST_OnUsersWithFullDataAndAgeBelow18ShouldReturnBadRequest400() throws Exception {
        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(
                                User.builder().firstName("Stefan").lastName("Stefanowsky").age(10).build()
                        ))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User must be at least 18 years old"));
    }

    @Test
    public void POST_OnUsersWithNoFirstNameShouldReturnBadRequest400() throws Exception {
        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(
                                User.builder().lastName("Stefanowsky").age(22).build()
                        ))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("First name is required"));

    }

    @Test
    public void GET_OnUsersWithIdShouldReturnUser() throws Exception {
        mockMvc.perform(get("/users/1"))
            .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Stefan"))
                .andExpect(jsonPath("$.lastName").value("Stefanowsky"))
                .andExpect(jsonPath("$.age").value(32))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void GET_OnUsersWithWrongIdShouldReturnNotFound404() throws Exception {
        mockMvc.perform(get("/users/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void GET_OnUsersWithStringAsIdShouldReturnBadRequest400() throws Exception {
        mockMvc.perform(get("/users/stefan"))
                .andExpect(status().isBadRequest());

    }

    private String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
}
