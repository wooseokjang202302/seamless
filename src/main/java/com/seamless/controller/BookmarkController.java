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
    public ResponseEntity<BookmarkResponseDto> addBookmark(@RequestBody BookmarkRequestDto bookmarkRequestDto) {
        BookmarkEntity addedBookmark = bookmarkService.addBookmark(bookmarkRequestDto);
        BookmarkResponseDto responseDto = convertToResponseDto(addedBookmark);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
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
        String message = String.valueOf(bookmarkService.deleteBookmark(userId, centerId));
        return ResponseEntity.ok(message);
    }

    private BookmarkResponseDto convertToResponseDto(BookmarkEntity entity) {
        BookmarkResponseDto dto = new BookmarkResponseDto();
        dto.setId(entity.getCenter().getId());
        dto.setName(entity.getCenter().getName());
        dto.setAddress(entity.getCenter().getAddress());
        dto.setEmail(entity.getCenter().getEmail());
        dto.setTel(entity.getCenter().getTel());
        dto.setHomepage(entity.getCenter().getHomepage());
        dto.setMapx(entity.getCenter().getMapx());
        dto.setMapy(entity.getCenter().getMapy());
        return dto;
    }
}
