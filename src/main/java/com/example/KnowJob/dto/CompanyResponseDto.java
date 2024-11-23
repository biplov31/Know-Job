package com.example.KnowJob.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponseDto {

    private Long id;
    private String name;
    private String email;
    private String address;
    private String description;
    private Float averageRating;

}

