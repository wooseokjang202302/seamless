package com.seamless.controller;

import com.seamless.dto.LoginRequestDto;
import com.seamless.dto.RefreshTokenRequestDto;
import com.seamless.dto.UserRequestDto;
import com.seamless.jwt.TokenUtil;
import com.seamless.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserRequestDto userRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok(userService.join(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenUtil.TokenResponse> login(@RequestBody LoginRequestDto loginRequestDto) throws AuthenticationException {
        TokenUtil.TokenResponse tokenResponse = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequestDto request) {
        TokenUtil.AccessTokenResponse accessTokenResponse = userService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(accessTokenResponse);
    }

}
