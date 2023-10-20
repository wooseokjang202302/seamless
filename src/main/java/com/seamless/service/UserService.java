package com.seamless.service;

import com.seamless.dto.UserRequestDto;
import com.seamless.entity.UserEntity;
import com.seamless.exception.NotFoundException;
import com.seamless.exception.UnauthorizedException;
import com.seamless.repository.UserRepository;
import com.seamless.jwt.TokenUtil;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDateTime;
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
        userEntity.setCreated_at(LocalDateTime.now());

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

    public TokenUtil.AccessTokenResponse refreshAccessToken(String refreshToken) {
        if (!tokenUtil.validateRefreshToken(refreshToken)) {
            throw new UnauthorizedException("리프레시 토큰이 만료되어 있습니다.");
        }

        String userId = tokenUtil.getUserIdFromRefreshToken(refreshToken);

        Optional<UserEntity> userOptional = userRepository.findById(Long.parseLong(userId));
        if (userOptional.isEmpty()) {
            throw new NotFoundException("리프레시 토큰에서 유저를 찾지 못했습니다.");
        }

        return tokenUtil.generateAccessToken(userOptional.get());
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
