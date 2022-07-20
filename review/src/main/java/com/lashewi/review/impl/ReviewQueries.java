package com.lashewi.review.impl;


import com.lashewi.review.model.Review;
import com.lashewi.review.repository.ReviewRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewQueries implements GraphQLQueryResolver {

    @NonNull
    private final ReviewRepository reviewRepository;

    public List<Review> reviews() {
        return reviewRepository.findAll();
    }

}