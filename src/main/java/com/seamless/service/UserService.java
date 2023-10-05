package com.seamless.service;

import com.seamless.domain.Users;
import com.seamless.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입
    public Long join(Users users) {
        validateDuplicateusers(users); //중복 회원 검증
        userRepository.save(users);
        return users.getId();
    }

    private void validateDuplicateusers(Users user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
}
