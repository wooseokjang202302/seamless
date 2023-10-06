package com.seamless;

import com.seamless.jwt.TokenUtil;
import com.seamless.repository.UserRepository;
import com.seamless.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;

    @Autowired
    public SpringConfig(UserRepository userRepository, TokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.tokenUtil = tokenUtil;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository, tokenUtil);
    }
}
