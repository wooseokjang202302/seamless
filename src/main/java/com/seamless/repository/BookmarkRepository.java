package com.seamless.repository;

import com.seamless.entity.BookmarkEntity;
import com.seamless.entity.CenterEntity;
import com.seamless.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
    List<BookmarkEntity> findByUser(UserEntity userEntity);
    Optional<BookmarkEntity> findByUserAndCenter(UserEntity userEntity, CenterEntity centerEntity);
}