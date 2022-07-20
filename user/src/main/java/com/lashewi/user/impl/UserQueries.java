package com.lashewi.user.impl;


import com.lashewi.user.User;
import com.lashewi.user.repository.UserRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueries implements GraphQLQueryResolver {

    @NonNull
    private final UserRepository userRepository;

    public List<User> users() {
        return userRepository.findAll();
    }

}