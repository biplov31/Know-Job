package com.example.KnowJob.mapper;

import com.example.KnowJob.dto.CompanyDto;
import com.example.KnowJob.model.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public Company map(CompanyDto companyDto) {
        return Company.builder()
                .name(companyDto.getName())
                .email(companyDto.getEmail())
                .address(companyDto.getAddress())
                .build();
    }

    public CompanyDto map(Company company) {
        return CompanyDto.builder()
                .name(company.getName())
                .email(company.getEmail())
                .address(company.getAddress())
                .build();
    }

}
