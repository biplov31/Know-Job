package com.example.KnowJob.repository;

import com.example.KnowJob.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM Review r WHERE r.company.id = :companyId")
    Float findAverageRatingByCompanyId(@Param("companyId") Long companyId);

}
