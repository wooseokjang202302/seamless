package com.seamless.service;

import com.seamless.dto.UserRequestDto;
import com.seamless.entity.UserEntity;
import com.seamless.repository.UserRepository;
import com.seamless.jwt.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, TokenUtil tokenUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenUtil = tokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입
    public String join(UserRequestDto userRequestDto) {
        validateDuplicateusers(userRequestDto); //중복 회원 검증
        validatepassword(userRequestDto); // 비밀번호 일치 검증

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userRequestDto.getEmail());
        
        // 비밀번호 해시화
        String rawPassword = userRequestDto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        userEntity.setPassword(encodedPassword);

        userEntity.setNickname(userRequestDto.getNickname());
        userEntity.setGender(userRequestDto.getGender());
        userEntity.setAge(userRequestDto.getAge());

        // 한국 시간으로 created_at 저장
        LocalDateTime koreatime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        userEntity.setCreated_at(koreatime);

        userRepository.save(userEntity);
        return userEntity.getEmail();
    }

    // 로그인
    public TokenUtil.TokenResponse login(String email, String password) throws AuthenticationException {
        Optional<UserEntity> usersOptional = userRepository.findByEmail(email);

        if (usersOptional.isEmpty()) {
            throw new AuthenticationException("해당 이메일이 존재하지 않습니다.");
        }

        UserEntity userEntity = usersOptional.get();

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new AuthenticationException("비밀번호가 일치하지 않습니다.");
        }
        return tokenUtil.generateToken(userEntity);
    }

    // 계정 중복여부 확인
    private void validateDuplicateusers(UserRequestDto userRequestDto) {

        if(userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 확인용 비밀번호 일치 확인
    private void validatepassword(UserRequestDto userRequestDto) {
        if(!userRequestDto.getPassword().equals(userRequestDto.getPassword2())) {
            throw new IllegalArgumentException("비밀번호와 확인용 비밀번호가 일치하지 않습니다.");
        }
    }
}
