package com.printease.application.controller;

import com.printease.application.security.dto.MessageWrapperDto;
import com.printease.application.security.dto.RatingCreateRequestDto;
import com.printease.application.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RatingsController {
    private final RatingService ratingService;
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<MessageWrapperDto> createRating(@Valid @RequestBody RatingCreateRequestDto ratingCreateRequestDto,
                                                          Principal principal) {
        return ratingService.createRating(principal.getName(),ratingCreateRequestDto);
    }
}
