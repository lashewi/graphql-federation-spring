package com.lashewi.user.config;

import com.apollographql.federation.graphqljava.Federation;
import com.apollographql.federation.graphqljava._Entity;
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
    public GraphQLSchema customSchema(SchemaParser schemaParser) {
        GraphQLSchema federatedSchema = Federation.transform(schemaParser.makeExecutableSchema())
                .fetchEntities(env -> env.<List<Map<String, Object>>>getArgument(_Entity.argumentName)
                        .stream()
                        .map(reference -> {
                            return null;
                        })
                        .collect(Collectors.toList()))
                .resolveEntityType(env -> {
                    return null;
                })
                .build();

        return federatedSchema;
    }
}