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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lmonkiewicz on 17.03.2017.
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class WebSimpleTests {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void GET_OnHelloShouldReturnHelloWorld() throws Exception {
        mockMvc.perform(
                get("/simple/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World!"));
    }

    @Test
    public void GET_OnHelloWithNameShouldReturnHelloName() throws Exception {
        mockMvc.perform(
                get("/simple/hello/{name}", "Stefan"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Stefan!"));
    }

    @Test
    public void GET_OnNameWithQueryParamShouldReturnFoundName() throws Exception {
        mockMvc.perform(
                get("/simple/name")
                    .param("query", "Stefan"))
                .andExpect(status().isOk())
                .andExpect(content().string("Stefan Stefanowsky"));
    }

    @Test
    public void GET_OnNameWithUnknownQueryParamShouldReturnNotFound404() throws Exception {
        mockMvc.perform(
                get("/simple/name")
                    .param("query", "Janek"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void GET_OnNameWithNoQueryParameterShouldReturnBadRequest400() throws Exception {
        mockMvc.perform(
                get("/simple/name"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Required String parameter 'query' is not present"));

    }

    @Test
    public void POST_OnUsersWithNoBodyShouldReturnBadRequest400() throws Exception {
        mockMvc.perform(
                post("/simple/users"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void POST_OnUsersWithFullDataShouldReturnIdAndCreatedStatus() throws Exception {
        mockMvc.perform(
                post("/simple/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(
                        Person.builder().firstName("Stefan").lastName("Stefanowsky").age(35).build()
                    ))
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/simple/users/1"))
                .andExpect(content().string("1"));
    }

    @Test
    public void POST_OnUsersWithNoFirstNameShouldReturnBadRequest400() throws Exception {
        mockMvc.perform(
                post("/simple/users")
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
