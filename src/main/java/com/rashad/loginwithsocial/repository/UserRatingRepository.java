package com.rashad.loginwithsocial.repository;

import com.rashad.loginwithsocial.entity.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRatingRepository extends JpaRepository<UserRating, Long> {

    UserRating findByGivenUserIdAndTakenUserId(Long givenId, Long takenId);

    UserRating findByTakenUserIdAndGivenUser_Username(Long id, String username);
}
