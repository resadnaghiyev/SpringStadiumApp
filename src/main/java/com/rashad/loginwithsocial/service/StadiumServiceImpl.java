package com.rashad.loginwithsocial.service;

import com.rashad.loginwithsocial.entity.*;
import com.rashad.loginwithsocial.model.RatingRequest;
import com.rashad.loginwithsocial.model.ReviewRequest;
import com.rashad.loginwithsocial.repository.*;
import com.rashad.loginwithsocial.service.impl.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StadiumServiceImpl implements StadiumService {

    private final StadiumRepository stadiumRepository;
    private final StdRatingRepository stdRatingRepository;
    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    @Override
    public Stadium getStadiumFromId(Long stadiumId) {
        Stadium stadium = stadiumRepository.findById(stadiumId).orElseThrow(() ->
                new IllegalStateException("stadium: Stadium with id: " + stadiumId + " not found"));
        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth != "anonymousUser") {
            UserDetails principal = (UserDetails) auth;
            StdRating stdRating = stdRatingRepository.findByStadiumIdAndUser_Username(
                    stadiumId, principal.getUsername());
            if (stdRating != null) {
                stadium.setUserRating(stdRating.getPoint());
            }
        }
        return stadium;
    }

    @Override
    public List<Stadium> getAllStadiums() {
        return stadiumRepository.findAll();
    }

    @Override
    public String addRatingToStadium(Long stadiumId, RatingRequest request) {
        Stadium stadium = stadiumRepository.findById(stadiumId).orElseThrow(() ->
                new IllegalStateException("stadium: Stadium with id: " + stadiumId + " not found"));
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(() ->
                new IllegalStateException("user: Not found"));
        StdRating stdRating = stdRatingRepository.findByStadiumIdAndUserId(stadiumId, user.getId());
        if (stdRating == null) {
            stdRating = new StdRating(request.getRating());
            stdRating.setStadium(stadium);
            stdRating.setUser(user);
        } else {
            stdRating.setPoint(request.getRating());
        }
        stdRatingRepository.save(stdRating);
        stadium.setRating(getStadiumAverageRating(stadium));
        stadiumRepository.save(stadium);
        return "Rating: " + request.getRating() + " added to stadium: " + stadium.getName();
    }

    @Override
    public String addReviewToStadium(Long stadiumId, ReviewRequest request) {
        Stadium stadium = stadiumRepository.findById(stadiumId).orElseThrow(() ->
                new IllegalStateException("stadium: Stadium with id: " + stadiumId + " not found"));
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(() ->
                new IllegalStateException("user: Not found"));
        List<User> userTags = new ArrayList<>();
        if (request.getUsernames() != null) {
            for (String username : request.getUsernames()) {
                Optional<User> userTag = userRepository.findByUsername(username);
                userTag.ifPresent(userTags::add);
            }
        }
        Review review = new Review(request.getText(), stadium, user, userTags);
        reviewRepository.save(review);
        return "Review added to stadium";
    }

    @Override
    public String addReplyToReview(Long reviewId, ReviewRequest request) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new IllegalStateException("review: Review with id: " + reviewId + " not found"));
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(() ->
                new IllegalStateException("user: Not found"));
        List<User> userTags = new ArrayList<>();
        if (request.getUsernames() != null) {
            for (String username : request.getUsernames()) {
                Optional<User> userTag = userRepository.findByUsername(username);
                userTag.ifPresent(userTags::add);
            }
        }
        Reply reply = new Reply(request.getText(), review, user, userTags);
        replyRepository.save(reply);
        return "Reply added to review";
    }

    @Override
    public List<Review> getStadiumReviews(Long stadiumId) {
        Stadium stadium = stadiumRepository.findById(stadiumId).orElseThrow(() ->
                new IllegalStateException("stadium: Stadium with id: " + stadiumId + " not found"));
        return stadium.getReviews();
    }

    public double getStadiumAverageRating(Stadium stadium) {
        int countRatings = stadium.getStdRatings().size();
        int sumRatings = 0;
        for (StdRating userStdRating : stadium.getStdRatings()) {
            sumRatings += userStdRating.getPoint();
        }
        double avg = (double) sumRatings / countRatings;
        avg = Math.round(avg*10.0) / 10.0;
        return avg;
    }
}
