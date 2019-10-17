package com.tw.apistackbase.service;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static org.springframework.data.domain.PageRequest.of;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Iterable<Company> findAll() {
        return companyRepository.findAll();
    }

    public Iterable<Company> findAllWithPageRequest(int page, int size) {
        return companyRepository.findAll(of(page, size));
    }

    public Company findByNameContaining(String name) {
        return companyRepository.findByNameContaining(name);
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Company update(String name, Long id) {
        Company company = findById(id);
        if (!isNull(company)) {
            company.setName(name);
            companyRepository.save(company);
        }
        return company;
    }

    public Company deleteById(Long id) {
        Company company = findById(id);
        if (!isNull(company)) {
            companyRepository.deleteById(id);
        }
        return company;
    }

    private Company findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
}
