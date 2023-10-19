package com.seamless.controller;

import com.seamless.dto.BookmarkRequestDto;
import com.seamless.entity.BookmarkEntity;
import com.seamless.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<BookmarkEntity> addBookmark(@RequestBody BookmarkRequestDto bookmarkRequestDto) {
        BookmarkEntity addedBookmark = bookmarkService.addBookmark(bookmarkRequestDto);
        return ResponseEntity.ok(addedBookmark);
    }

    // 특정 사용자의 모든 북마크 가져오기
    @GetMapping("/{userId}")
    public ResponseEntity<List<BookmarkEntity>> getBookmarksByUserId(@PathVariable Long userId) {
        List<BookmarkEntity> bookmarks = bookmarkService.getBookmarksByUserId(userId);
        return ResponseEntity.ok(bookmarks);
    }

    // 북마크 삭제
    @DeleteMapping("/{userId}/{centerId}")
    public ResponseEntity<String> deleteBookmark(@PathVariable Long userId, @PathVariable Long centerId) {
        String message = String.valueOf(bookmarkService.deleteBookmark(userId, centerId));
        return ResponseEntity.ok(message);
    }
}
