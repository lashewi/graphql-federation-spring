package com.lashewi.review.config;

import com.apollographql.federation.graphqljava.SchemaTransformer;
import com.apollographql.federation.graphqljava._Entity;
import com.lashewi.review.model.Review;
import com.lashewi.review.resolver.ReviewReferenceResolver;
import graphql.schema.GraphQLSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FederatedSchema {

    @Bean
    public GraphQLSchema federatedGraphQLSchema(SchemaTransformer schemaTransformer) {

        String federationTypeName = "Review";

        return schemaTransformer.fetchEntities(env -> env.<List<Map<String, Object>>>getArgument(_Entity.argumentName)
                        .stream()
                        .map(reference -> {
                            if (federationTypeName.equals(reference.get("__typename"))) {
                                return ReviewReferenceResolver.resolveReference(reference);
                            }
                            return null;
                        })
                        .collect(Collectors.toList()))
                .resolveEntityType(env -> {
                    final Object src = env.getObject();
                    if (src instanceof Review) {
                        return env.getSchema()
                                .getObjectType(federationTypeName);
                    }
                    return null;
                })
                .build();
    }
}