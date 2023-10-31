package com.seamless.controller;

import com.seamless.dto.CenterResponseDto;
import com.seamless.repository.CenterRepository;
import com.seamless.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/centers")
public class CenterController {
    private final CenterService centerService;
    private final CenterRepository centerRepository;

    @Autowired
    public CenterController(CenterService centerService, CenterRepository centerRepository) {
        this.centerService = centerService;
        this.centerRepository = centerRepository;
    }

    @GetMapping
    public ResponseEntity<List<CenterResponseDto>> getAllCenters() {
        List<CenterResponseDto> centers = centerService.getAllCenters();
        return ResponseEntity.ok(centers);
    }

    @GetMapping("/area/do_si")
    public ResponseEntity<List<String>> getUniqueDoSi() {
        List<String> doSiList = centerRepository.findUniqueDoSi();
        return ResponseEntity.ok(doSiList);
    }

    @GetMapping("/area/si_gun_gu")
    public ResponseEntity<List<String>> getSiGunGuByDoSi(@RequestParam String do_si) {
        List<String> siGunGuList = centerRepository.findSiGunGuByDoSi(do_si);
        return ResponseEntity.ok(siGunGuList);
    }

    @GetMapping("/area/dong")
    public ResponseEntity<List<String>> getDongBySiGunGu(@RequestParam String si_gun_gu) {
        List<String> dongList = centerRepository.findDongBySiGunGu(si_gun_gu);
        return ResponseEntity.ok(dongList);
    }
}
