package com.printease.application.controller;

import com.printease.application.dto.MessageWrapperDto;
import com.printease.application.dto.RatingCreateRequestDto;
import com.printease.application.dto.RatingDto;
import com.printease.application.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<RatingDto>> getRating(@PathVariable Long id, Principal principal) {
        return ratingService.getRating(id, principal.getName());
    }

}
