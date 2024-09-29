package com.example.KnowJob.service;

import com.example.KnowJob.dto.CompanyDto;
import com.example.KnowJob.mapper.CompanyMapper;
import com.example.KnowJob.model.Company;
import com.example.KnowJob.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyDto addCompany(CompanyDto companyDto) {
        Company company = companyMapper.map(companyDto);

        Company savedCompany = companyRepository.save(company);
        if (savedCompany.getId() != null) {
            return companyMapper.map(savedCompany);
        } else {
            throw new RuntimeException("Failed to add company.");
        }
    }

}
