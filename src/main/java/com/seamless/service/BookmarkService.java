package com.seamless.service;

import com.seamless.dto.BookmarkRequestDto;
import com.seamless.entity.BookmarkEntity;
import com.seamless.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    @Autowired
    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }
    
    // 북마크 추가
    public BookmarkEntity addBookmark(BookmarkRequestDto bookmarkRequestDto) {

        validateExistingBookmark(bookmarkRequestDto);   // 해당 북마크 존재 확인

        BookmarkEntity bookmarkEntity = new BookmarkEntity();
        bookmarkEntity.setUserId(bookmarkRequestDto.getUserId());
        bookmarkEntity.setPostId(bookmarkRequestDto.getPostId());

        return bookmarkRepository.save(bookmarkEntity);
    }

    // 모든 북마크 가져오기
    public List<BookmarkEntity> getBookmarksByUserId(Long userId) {
        return bookmarkRepository.findByUserId(userId);
    }

    // 북마크 삭제
    public ResponseEntity<String> deleteBookmark(Long userId, Long postId) {
        Optional<BookmarkEntity> optionalBookmark = bookmarkRepository.findByUserIdAndPostId(userId, postId);

        if (optionalBookmark.isEmpty()) {
            throw new IllegalArgumentException("등록되지 않은 북마크입니다.");
        }

        bookmarkRepository.delete(optionalBookmark.get());
        return ResponseEntity.ok("북마크를 삭제했습니다.");
    }

    // 이미 등록된 북마크인지 확인
    private void validateExistingBookmark(BookmarkRequestDto bookmarkRequestDto) {
        if(bookmarkRepository.findByUserIdAndPostId(bookmarkRequestDto.getUserId(), bookmarkRequestDto.getPostId()).isPresent()) {
            throw new IllegalArgumentException("이미 등록된 북마크입니다.");
        }
    }
}

