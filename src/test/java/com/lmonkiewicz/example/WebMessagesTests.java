package com.lmonkiewicz.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmonkiewicz.example.model.Message;
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
public class WebMessagesTests {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void POST_OnMessageWithFullDataReturnsCreated() throws Exception {
        mockMvc.perform(
                post("/messages")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(
                            Message.builder().title("Hello World").message("Hello everybody!").build()
                    )))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/messages/1"))
                .andExpect(content().string("1"));
    }

    @Test
    public void POST_OnMessagesWithEmptyDataRuturnsBadRequest400() throws Exception {
        mockMvc.perform(
                post("/messages")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(
                            Message.builder().build()
                    )))
                .andExpect(status().isBadRequest());

    }

    private String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

}
