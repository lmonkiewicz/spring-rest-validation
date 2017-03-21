package com.lmonkiewicz.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmonkiewicz.example.model.Address;
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
public class WebAddressesTests {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void POST_OnAddressesWithFullDataReturnsCreated() throws Exception {
        mockMvc.perform(
                post("/addresses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(
                            Address.builder().company(true).name("Awesome Inc.").taxId("333444555").street("Awesome 42").build()
                    )))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/addresses/1"))
                .andExpect(content().string("1"));
    }

    @Test
    public void POST_OnAddressesWithOnlyNameAndStreetReturnsCreated() throws Exception {
        mockMvc.perform(
                post("/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(
                                Address.builder().name("Awesome Inc.").street("Awesome 42").build()
                        )))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/addresses/1"))
                .andExpect(content().string("1"));
    }


    @Test
    public void POST_OnAddressesWithCompanyAndNoTaxIdReturnsBadRequest400() throws Exception {
        mockMvc.perform(
                post("/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(
                                Address.builder().company(true).name("Awesome Inc.").build()
                        ))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("TaxId is required for company address"));
    }

    private String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

}
