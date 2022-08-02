package com.lashewi.review.config;

import com.apollographql.federation.graphqljava.Federation;
import com.apollographql.federation.graphqljava._Entity;
import com.lashewi.review.impl.ReviewQueries;
import com.lashewi.review.model.Review;
import graphql.kickstart.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FederatedSchema {

    @Bean
    public GraphQLSchema customSchema(SchemaParser schemaParser, ReviewQueries reviewQueries){
        return Federation.transform(schemaParser.makeExecutableSchema())
                .fetchEntities(env -> env.<List<Map<String, Object>>>getArgument(_Entity.argumentName)
                        .stream()
                        .map(values -> {
                            if ("Review".equals(values.get("__typename"))) {
                                final Long id =  Long.valueOf((String) values.get("id"));
                                if (id instanceof Long) {
                                    return reviewQueries.lookupReview(id);
                                }
                            }
                            return null;
                        })
                        .collect(Collectors.toList()))
                .resolveEntityType(env -> {
                    final Object src = env.getObject();
                    if (src instanceof Review) {
                        return env.getSchema().getObjectType("Review");
                    }
                    return null;
                })
                .build();
    }
}