package com.seamless.service;

import com.seamless.domain.Users;
import com.seamless.repository.UserRepository;
import com.seamless.jwt.TokenUtil;

import javax.security.sasl.AuthenticationException;
import java.util.Objects;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;

    public UserService(UserRepository userRepository, TokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.tokenUtil = tokenUtil;
    }

    // 회원가입
    public Long join(Users users) {
        validateDuplicateusers(users); //중복 회원 검증
        userRepository.save(users);
        return users.getId();
    }

    // 로그인
    public String login(String email, String password) throws AuthenticationException {
        Optional<Users> usersOptional = userRepository.findByEmail(email);

        if (usersOptional.isEmpty()) {
            throw new AuthenticationException("해당 이메일이 존재하지 않습니다.");
        }

        Users user = usersOptional.get();

        boolean passwordMatch = Objects.equals(user.getPassword(), password);
        if (!passwordMatch) {
            throw new AuthenticationException("비밀번호가 일치하지 않습니다.");
        }
        return tokenUtil.generateToken(user).toString();
    }

    private void validateDuplicateusers(Users user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
}
