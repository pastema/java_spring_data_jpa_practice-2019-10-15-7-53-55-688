package com.tw.apistackbase.service;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import static org.springframework.data.domain.PageRequest.of;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Iterable<Company> findAllWithPageRequest(int page, int size) {
        return companyRepository.findAll(of(page, size));
    }

    public Company findByNameContaining(String name) {
        return companyRepository.findByNameContaining(name);
    }

    public HttpEntity save(Company company) {
        companyRepository.save(company);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public HttpEntity update(Company company, String name) {
        company.setName(name);
        companyRepository.save(company);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Company findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public HttpEntity deleteById(Long id) {
        companyRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
    





