package com.seamless.controller;

import com.seamless.dto.BookmarkRequestDto;
import com.seamless.dto.BookmarkResponseDto;
import com.seamless.entity.BookmarkEntity;
import com.seamless.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    // 북마크 추가
    @PostMapping
    public ResponseEntity<String> addBookmark(@RequestBody BookmarkRequestDto bookmarkRequestDto) {
        bookmarkService.addBookmark(bookmarkRequestDto);
        return new ResponseEntity<>("북마크를 추가했습니다", HttpStatus.CREATED);
    }


    // 특정 사용자의 모든 북마크 가져오기
    @GetMapping("/{userId}")
    public ResponseEntity<List<BookmarkResponseDto>> getBookmarksByUserId(@PathVariable Long userId) {
        List<BookmarkResponseDto> bookmarks = bookmarkService.getBookmarksByUserId(userId);
        return ResponseEntity.ok(bookmarks);
    }

    // 북마크 삭제
    @DeleteMapping("/{userId}/{centerId}")
    public ResponseEntity<String> deleteBookmark(@PathVariable Long userId, @PathVariable Long centerId) {
        return bookmarkService.deleteBookmark(userId, centerId);
    }
}
