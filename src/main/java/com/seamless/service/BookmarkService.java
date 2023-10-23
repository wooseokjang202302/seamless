package com.seamless.service;

import com.seamless.dto.BookmarkRequestDto;
import com.seamless.dto.BookmarkResponseDto;
import com.seamless.entity.BookmarkEntity;
import com.seamless.entity.CenterEntity;
import com.seamless.entity.UserEntity;
import com.seamless.repository.BookmarkRepository;
import com.seamless.repository.CenterRepository;
import com.seamless.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final CenterRepository centerRepository;

    @Autowired
    public BookmarkService(BookmarkRepository bookmarkRepository, UserRepository userRepository, CenterRepository centerRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.centerRepository = centerRepository;
    }

    // 북마크 추가
    public BookmarkEntity addBookmark(BookmarkRequestDto bookmarkRequestDto) {
        validateExistingBookmark(bookmarkRequestDto);

        UserEntity userEntity = userRepository.findById(bookmarkRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        CenterEntity centerEntity = centerRepository.findById(bookmarkRequestDto.getCenterId())
                .orElseThrow(() -> new IllegalArgumentException("센터를 찾을 수 없습니다."));

        BookmarkEntity bookmarkEntity = new BookmarkEntity();
        bookmarkEntity.setUser(userEntity);
        bookmarkEntity.setCenter(centerEntity);

        return bookmarkRepository.save(bookmarkEntity);
    }

    // 모든 북마크 가져오기
    public List<BookmarkResponseDto> getBookmarksByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        List<BookmarkEntity> bookmarkEntities = bookmarkRepository.findByUser(user);

        return bookmarkEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // BookmarkEntity를 BookmarkResponseDto로 변환하는 메서드
    private BookmarkResponseDto convertToDto(BookmarkEntity bookmarkEntity) {
        BookmarkResponseDto dto = new BookmarkResponseDto();

        CenterEntity center = bookmarkEntity.getCenter();

        dto.setId(center.getId());
        dto.setName(center.getName());
        dto.setAddress(center.getAddress());
        dto.setEmail(center.getEmail());
        dto.setTel(center.getTel());
        dto.setHomepage(center.getHomepage());
        dto.setMapx(center.getMapx());
        dto.setMapy(center.getMapy());

        return dto;
    }

    // 북마크 삭제
    public ResponseEntity<String> deleteBookmark(Long userId, Long centerId) {
        // 유저 객체 찾기
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 센터 객체 찾기
        CenterEntity center = centerRepository.findById(centerId)
                .orElseThrow(() -> new IllegalArgumentException("센터를 찾을 수 없습니다."));

        // 북마크 객체 찾기
        Optional<BookmarkEntity> optionalBookmark = bookmarkRepository.findByUserAndCenter(user, center);

        if (optionalBookmark.isEmpty()) {
            throw new IllegalArgumentException("등록되지 않은 북마크입니다.");
        }

        // 북마크 삭제
        bookmarkRepository.delete(optionalBookmark.get());
        return ResponseEntity.ok("북마크를 삭제했습니다.");
    }

    // 이미 등록된 북마크인지 확인
    private void validateExistingBookmark(BookmarkRequestDto bookmarkRequestDto) {
        UserEntity user = userRepository.findById(bookmarkRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        CenterEntity center = centerRepository.findById(bookmarkRequestDto.getCenterId())
                .orElseThrow(() -> new IllegalArgumentException("센터를 찾을 수 없습니다."));

        if (bookmarkRepository.findByUserAndCenter(user, center).isPresent()) {
            throw new IllegalArgumentException("이미 등록된 북마크입니다.");
        }
    }
}

