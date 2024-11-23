package com.example.KnowJob.service;

import com.example.KnowJob.dto.CompanyDto;
import com.example.KnowJob.dto.CompanyResponseDto;
import com.example.KnowJob.mapper.CompanyMapper;
import com.example.KnowJob.model.Company;
import com.example.KnowJob.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyResponseDto addCompany(CompanyDto companyDto) {
        Company company = companyMapper.map(companyDto);

        Company savedCompany = companyRepository.save(company);
        if (savedCompany.getId() != null) {
            return companyMapper.map(savedCompany);
        } else {
            throw new RuntimeException("Failed to add company.");
        }
    }

    public List<CompanyResponseDto> getCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(company -> companyMapper.map(company))
                .toList();

    }

    public CompanyResponseDto getCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found."));

        Float averageRating = companyRepository.findAverageRatingByCompanyId(companyId);

        CompanyResponseDto companyResponseDto = companyMapper.map(company);
        companyResponseDto.setAverageRating(averageRating);
        return companyResponseDto;
    }

}
