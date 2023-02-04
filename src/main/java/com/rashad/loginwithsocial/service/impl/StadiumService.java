package com.rashad.loginwithsocial.service.impl;

import com.rashad.loginwithsocial.entity.Review;
import com.rashad.loginwithsocial.entity.Stadium;
import com.rashad.loginwithsocial.model.RatingRequest;
import com.rashad.loginwithsocial.model.ReviewRequest;

import java.util.List;

public interface StadiumService {

    Stadium getStadiumFromId(Long stadiumId);

    List<Stadium> getAllStadiums();

    String addRatingToStadium(Long stadiumId, RatingRequest request);

    String addReviewToStadium(Long stadiumId, ReviewRequest request);

    List<Review> getStadiumReviews(Long stadiumId);

    String addReplyToReview(Long reviewId, ReviewRequest request);
}
