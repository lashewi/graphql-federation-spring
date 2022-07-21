package com.lashewi.review.resolver;

import com.lashewi.review.model.Review;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class ReviewReferenceResolver {

    public static Review resolveReference(@NotNull Map<String, Object> reference) {
        String federatedKeyName = "id";

        if (!(reference.get(federatedKeyName) instanceof String)) {
            return null;
        }

        final Long id = Long.valueOf((String) reference.get(federatedKeyName));

        Review review = new Review();
        review.setId(id);
        return review;
    }
}
