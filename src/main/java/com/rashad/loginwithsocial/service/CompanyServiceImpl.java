package com.rashad.loginwithsocial.service;

import com.rashad.loginwithsocial.entity.Company;
import com.rashad.loginwithsocial.entity.Stadium;
import com.rashad.loginwithsocial.repository.CompanyRepository;
import com.rashad.loginwithsocial.service.impl.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Company getCompanyFromId(Long companyId) {
        return companyRepository.findById(companyId).orElseThrow(() ->
                new IllegalStateException("company: Company with id: " + companyId + " is not found"));
    }

    @Override
    public Map<String, Object> getAllCompanies(String page, String size, String sort, String sortDir) {
        Pageable pageable;
        if (page != null && size != null && sort != null && sortDir != null) {
            pageable = PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size),
                    sortDir.equals("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        } else if (page != null && sort != null && sortDir != null) {
            pageable = PageRequest.of(Integer.parseInt(page) - 1, 2,
                    sortDir.equals("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        } else if (page != null && size != null) {
            pageable = PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size));
        } else if (page != null) {
            pageable = PageRequest.of(Integer.parseInt(page) - 1, 2);
        } else {
            pageable = PageRequest.of(0, 2);
        }
        Page<Company> companies = companyRepository.findAll(pageable);

        Map<String, Object> data = new HashMap<>();
        data.put("totalElements", companies.getTotalElements());
        data.put("totalPages", companies.getTotalPages());
        data.put("previousPage", companies.getPageable().previousOrFirst().getPageNumber() + 1);
        data.put("currentPage", companies.getPageable().getPageNumber() + 1);
        data.put("nextPage", companies.getPageable().next().getPageNumber() + 1);
        data.put("content", companies.getContent());
        return data;
    }

    @Override
    public List<Stadium> getStadiumsFromCompanyId(Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() ->
                new IllegalStateException("company: Company with id: " + companyId + " is not found"));
        return company.getStadiums();
    }

    @Override
    public Page<Company> getCompaniesFromSearchBar(String keyword) {
        Pageable pageable = PageRequest.of(0, 2);
        return companyRepository.findAllSearchBar(keyword, pageable);
    }
}