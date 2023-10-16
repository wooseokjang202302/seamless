package com.seamless.service;

import com.seamless.dto.CenterResponseDto;
import com.seamless.repository.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CenterService {
    private final CenterRepository centerRepository;

    @Autowired
    public CenterService(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    public List<CenterResponseDto> getAllCenters() {
        return centerRepository.findAll().stream()
                .map(CenterResponseDto::new)
                .collect(Collectors.toList());
    }
}