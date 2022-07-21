package com.lashewi.user.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lashewi.user.User;
import com.lashewi.user.model.Product;
import com.lashewi.user.repository.UserRepository;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserQueries implements GraphQLQueryResolver {

    private static final String URL = "http://localhost:4000/graphql";

    @NonNull
    private final UserRepository userRepository;

    public List<User> users() {
        return userRepository.findAll();
    }

    public User me() {
        User user = new User();
        user.setUserId(55L);

        ObjectMapper objectMapper = new ObjectMapper();

        WebClient webClient = WebClient.builder().baseUrl(URL).build();
        GraphQLWebClient graphqlClient = GraphQLWebClient.newInstance(webClient, objectMapper);

        Product callbackResponse = graphqlClient.post("graphql/test-me.graphql",
                        Map.of("id", 1), Product.class)
                .block();

        user.setFirstName(callbackResponse.getName());
        user.setLastName("First name should be product name");
        return user;
    }


}