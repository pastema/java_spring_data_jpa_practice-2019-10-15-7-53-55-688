package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Company getCompanyByName(@RequestParam(required = false, defaultValue = "") String name) {
        return companyService.findByNameContaining(name);
    }


    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public HttpEntity addCompany(@RequestBody Company company) {
        if (isNull(company)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return companyService.save(company);
    }

    @PatchMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public HttpEntity updateCompany(@RequestBody Company company, @PathVariable Long id) {
        Company updatedCompany = companyService.findById(id);
        if (isNull(updatedCompany)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return companyService.update(updatedCompany, company.getName());
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public HttpEntity deleteCompany(@PathVariable Long id) {
        Company company = companyService.findById(id);
        if (isNull(company)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return companyService.deleteById(id);
    }
}