package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
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

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public Iterable<Company> list(@RequestParam(required = false, defaultValue = "") Integer page,
                                  @RequestParam(required = false, defaultValue = "") Integer size) {
        if (!isNull(page) && !isNull(size)) {
            return companyService.findAllWithPageRequest(page, size);
        }
        return companyService.findAll();
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Company getCompanyByName(@RequestParam(required = false, defaultValue = "") String name) {
        return companyService.findByNameContaining(name);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public HttpEntity addCompany(@RequestBody Company company) {
        Company isSaved = companyService.save(company);
        if (isNull(isSaved)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(isSaved, HttpStatus.CREATED);
    }

    @PatchMapping(produces = APPLICATION_JSON_VALUE)
    public HttpEntity updateCompany(@RequestBody Company company,
                                    @RequestParam(required = false, defaultValue = "") long id) {
        Company isUpdated = companyService.update(company.getName(), id);
        if (isNull(isUpdated)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(isUpdated, HttpStatus.OK);
    }

    @DeleteMapping(produces = APPLICATION_JSON_VALUE)
    public HttpEntity deleteCompany(@RequestParam(required = false, defaultValue = "") long id) {
        Company company = companyService.deleteById(id);
        if (isNull(company)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
