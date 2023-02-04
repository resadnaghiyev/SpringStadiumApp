package com.rashad.loginwithsocial.repository;

import com.rashad.loginwithsocial.entity.StdRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StdRatingRepository extends JpaRepository<StdRating, Long> {

    Optional<StdRating> findByPoint(Integer point);

    StdRating findByStadiumIdAndUserId(Long stadiumId, Long userId);

    StdRating findByStadiumIdAndUser_Username(Long stadiumId, String username);
}
