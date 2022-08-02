package com.lashewi.product.config;

import com.apollographql.federation.graphqljava.Federation;
import com.apollographql.federation.graphqljava._Entity;
import com.lashewi.product.impl.ProductQueries;
import com.lashewi.product.model.Product;
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
    public GraphQLSchema customSchema(SchemaParser schemaParser, ProductQueries productQueries){
        return Federation.transform(schemaParser.makeExecutableSchema())
                .fetchEntities(env -> env.<List<Map<String, Object>>>getArgument(_Entity.argumentName)
                        .stream()
                        .map(values -> {
                            System.out.println("TEST 0" + values.get("__typename"));
                            if ("Product".equals(values.get("__typename"))) {
                                final Long id =  Long.valueOf((String) values.get("id"));
                                if (id instanceof Long) {
                                    return productQueries.lookupProduct(id);
                                }
                            }
                            return null;
                        })
                        .collect(Collectors.toList()))
                .resolveEntityType(env -> {
                    final Object src = env.getObject();
                    if (src instanceof Product) {
                        return env.getSchema().getObjectType("Product");
                    }
                    return null;
                })
                .build();
    }
}