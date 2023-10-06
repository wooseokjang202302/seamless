package com.seamless.controller;

import com.seamless.domain.Users;
import com.seamless.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public Long join(@RequestBody Users user) {
        return userService.join(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) throws AuthenticationException {
        return userService.login(user.getEmail(), user.getPassword());
    }

}
