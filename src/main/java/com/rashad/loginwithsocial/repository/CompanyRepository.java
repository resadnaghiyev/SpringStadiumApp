package com.rashad.loginwithsocial.repository;

import com.rashad.loginwithsocial.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c WHERE " +
            "CONCAT(c.id, ' ', c.name, ' ', c.address, ' ', c.user.username) " +
            "LIKE %:keyword%")
    Page<Company> findAllSearchBar(String keyword, Pageable pageable);
}
