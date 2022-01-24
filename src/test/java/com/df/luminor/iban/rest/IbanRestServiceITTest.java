package com.df.luminor.iban.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.df.luminor.iban.dto.IbanDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static com.df.luminor.TestHelper.getInvalidIbanAsString;
import static com.df.luminor.TestHelper.getValidIbanAsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(IbanRestService.class)
class IbanRestServiceITTest {

    @MockBean
    IbanRestService ibanRestService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void whenGetAllIbans_thenReturnValidAndInvalid() throws Exception {

        // GIVEN
        var ibanDto1 = new IbanDTO(getInvalidIbanAsString(), false);
        var ibanDto2 = new IbanDTO(getValidIbanAsString(), true);
        var expectedJson = objectMapper.writeValueAsString(new IbanDTO[]{ibanDto1, ibanDto2});

        // WHEN
        when(ibanRestService.getIbans(Filter.ALL)).thenReturn(List.of(ibanDto1, ibanDto2));

        // THEN
        mockMvc.perform(get("/api/ibans?filter=ALL"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson, false))
        ;
    }


}