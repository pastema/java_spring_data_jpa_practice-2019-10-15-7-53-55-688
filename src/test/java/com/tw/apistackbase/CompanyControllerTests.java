package com.tw.apistackbase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.apistackbase.controller.CompanyController;
import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.service.CompanyService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompanyController.class)
@ActiveProfiles(profiles = "test")
public class CompanyControllerTests {

    @MockBean
    private CompanyService companyService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_delete() throws Exception {
        Company company = new Company("OOCL");
        when(companyService.deleteById(any())).thenReturn(company);

        ResultActions result = mvc.perform(delete("/companies?id=1"));

        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void should_get_all() throws Exception {
        when(companyService.findAll()).thenReturn(dummyCompanies());
        ResultActions result = mvc.perform(get("/companies/all"));

        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("OOCL")));
    }

    @Test
    void should_get_by_id() throws Exception {
        Company company = new Company("OOCL");
        when(companyService.findByNameContaining(any())).thenReturn(company);
        ResultActions result = mvc.perform(get("/companies?name=OOCL"));

        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is("OOCL")));
    }

    private List<Company> dummyCompanies() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("OOCL"));

        return companies;
    }

}
