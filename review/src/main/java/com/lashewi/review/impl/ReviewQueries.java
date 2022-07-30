package com.lashewi.review.impl;


import com.lashewi.review.model.Review;
import com.lashewi.review.repository.ReviewRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewQueries implements GraphQLQueryResolver {

    @NonNull
    private final ReviewRepository reviewRepository;

    public List<Review> reviews() {
        log.info("Loading reviews");
        return reviewRepository.findAll();
    }

}