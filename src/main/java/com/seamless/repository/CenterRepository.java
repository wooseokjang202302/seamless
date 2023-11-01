package com.seamless.repository;

import com.seamless.entity.CenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CenterRepository extends JpaRepository<CenterEntity, Long> {
    @Query("SELECT DISTINCT c.do_si FROM CenterEntity c ORDER BY c.do_si")
    List<String> findUniqueDoSi();

    @Query("SELECT DISTINCT c.si_gun_gu FROM CenterEntity c WHERE c.do_si = ?1 ORDER BY c.si_gun_gu")
    List<String> findSiGunGuByDoSi(String do_si);

    @Query("SELECT DISTINCT c.dong FROM CenterEntity c WHERE c.si_gun_gu = ?1 ORDER BY c.dong")
    List<String> findDongBySiGunGu(String si_gun_gu);
}