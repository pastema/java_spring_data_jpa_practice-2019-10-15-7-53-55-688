package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @GetMapping(produces = {APPLICATION_JSON_VALUE})
    public Iterable<Company> list() {
        return  companyRepository.findAll();
    }

    @GetMapping(value = "/{value}",produces = {APPLICATION_JSON_VALUE})
    public Company getCompanyByName(@PathVariable String name) {
        return  companyRepository.findOneByName(name);
    }

    @PostMapping(produces = {APPLICATION_JSON_VALUE})
    public Company add(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    @PutMapping(value = "/{id}",produces = {APPLICATION_JSON_VALUE})
    public Company updateCompany(@RequestBody Company company, @PathVariable Long id){
        companyRepository.findById(id).ifPresent(value -> company.setId(value.getId()));
        return companyRepository.save(company);
    }


    @DeleteMapping(value = "/{id}",produces = {APPLICATION_JSON_VALUE})
    public String deleteCompany(@PathVariable Long id) {
        companyRepository.deleteById(id);
        return  "Deleted";
    }
}

