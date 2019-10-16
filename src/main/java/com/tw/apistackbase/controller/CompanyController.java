package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @GetMapping(produces = {"application/json"})
    public Iterable<Company> list() {
        return  companyRepository.findAll();
    }

    @GetMapping(value = "/{value}",produces = {"application/json"})
    public Company getCompanyByName(@PathVariable String name) {
        return  companyRepository.findOneByName(name);
    }

    @PostMapping(produces = {"application/json"})
    public Company add(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    @DeleteMapping(value = "/{id}",produces = {"application/json"})
    public String deleteCompany(@PathVariable Long id) {
        companyRepository.deleteById(id);
        return  "Deleted";
    }
}

