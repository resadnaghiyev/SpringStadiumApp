package com.rashad.loginwithsocial.service.impl;

import com.rashad.loginwithsocial.entity.Company;
import com.rashad.loginwithsocial.entity.Stadium;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CompanyService {

    Company getCompanyFromId(Long companyId);

    Map<String, Object> getAllCompanies(String page, String size, String sort, String sortDir);

    List<Stadium> getStadiumsFromCompanyId(Long companyId);

    Page<Company> getCompaniesFromSearchBar(String keyword);
}
