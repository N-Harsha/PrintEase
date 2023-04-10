package com.printease.application.service;

import com.printease.application.model.Orientation;
import com.printease.application.repository.OrientationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrientationService {
    private final OrientationRepository orientationRepository;
    Orientation getOrientation(Long id){
        return orientationRepository.findById(id).orElse(null);
    }

}
