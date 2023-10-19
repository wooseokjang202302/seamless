package com.seamless.repository;

import com.seamless.entity.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
    List<BookmarkEntity> findByUserId(Long userId);
    Optional<BookmarkEntity> findByUserIdAndCenterId(Long userId, Long centerId);
}