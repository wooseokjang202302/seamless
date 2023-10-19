package com.seamless.repository;

import com.seamless.entity.CenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CenterRepository extends JpaRepository<CenterEntity, Long> {
}