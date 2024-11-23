package com.example.KnowJob.controller;

import com.example.KnowJob.dto.CompanyDto;
import com.example.KnowJob.dto.CompanyResponseDto;
import com.example.KnowJob.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponseDto> addCompany(@RequestBody CompanyDto companyDto) {
        CompanyResponseDto addedCompany = companyService.addCompany(companyDto);

        if (addedCompany != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(addedCompany);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> getCompanies() {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> getCompany(@PathVariable Long id) {
        CompanyResponseDto companyResponseDto = companyService.getCompany(id);
        return ResponseEntity.status(HttpStatus.OK).body(companyResponseDto);
    }

}
