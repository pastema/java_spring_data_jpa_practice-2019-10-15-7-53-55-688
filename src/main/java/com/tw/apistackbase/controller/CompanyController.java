package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @GetMapping(value = "/all", produces = {APPLICATION_JSON_VALUE})
    public Iterable<Company> list() {
        return  companyRepository.findAll();
    }

    @GetMapping(produces = {APPLICATION_JSON_VALUE})
    public Company getCompanyByName(@RequestParam String name) {
        return  companyRepository.findByNameContaining(name);
    }

    @PostMapping(produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.CREATED)
    public Company add(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    @PatchMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public HttpEntity updateCompany(@RequestBody Company company, @PathVariable Long id) {
        Optional<Company> updatedCompany = companyRepository.findById(id);
        if (updatedCompany.isPresent()) {
            updatedCompany.get().setName(company.getName());
            companyRepository.save(updatedCompany.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(value = "/{id}",produces = {APPLICATION_JSON_VALUE})
    public HttpEntity deleteCompany(@PathVariable Long id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            companyRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}