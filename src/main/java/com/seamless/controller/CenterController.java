package com.seamless.controller;

import com.seamless.dto.CenterResponseDto;
import com.seamless.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/centers")
public class CenterController {
    private final CenterService centerService;

    @Autowired
    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }

    @GetMapping
    public ResponseEntity<List<CenterResponseDto>> getAllCenters() {
        List<CenterResponseDto> centers = centerService.getAllCenters();
        return ResponseEntity.ok(centers);
    }
}
