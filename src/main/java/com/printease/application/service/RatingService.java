package com.printease.application.service;

import com.printease.application.dto.RatingDto;
import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import com.printease.application.mapper.RatingsMapper;
import com.printease.application.model.Customer;
import com.printease.application.model.Rating;
import com.printease.application.model.ServiceProvider;
import com.printease.application.repository.RatingRepository;
import com.printease.application.dto.MessageWrapperDto;
import com.printease.application.dto.RatingCreateRequestDto;
import com.printease.application.utils.ExceptionMessageAccessor;
import com.printease.application.utils.GeneralMessageAccessor;
import com.printease.application.utils.ProjectConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final CustomerService customerService;
    private final ServiceOfServiceProvider serviceOfServiceProvider;
    private final GeneralMessageAccessor generalMessageAccessor;
    private final ExceptionMessageAccessor exceptionMessageAccessor;

    @Transactional
    public ResponseEntity<MessageWrapperDto> createRating(String email, RatingCreateRequestDto ratingCreateRequestDto) {
        Customer customer = customerService.findCustomerByUserEmail(email);
        ServiceProvider serviceProvider =
                serviceOfServiceProvider.findById(ratingCreateRequestDto.getServiceProviderId());
        log.info("Customer with id {} is trying to created rating for service provider with id {}",
                customer.getId(), serviceProvider.getId());
        if (isRatingExists(customer, serviceProvider)) {
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(null,
                    ProjectConstants.RATING_ALREADY_GIVEN, serviceProvider.getId()), HttpStatus.BAD_REQUEST,
                    LocalDateTime.now()));
        }
        log.info("Customer with id: {} has not rated the service provider with id: {} yet.", customer.getId(),
                serviceProvider.getId());
        Rating rating = Rating.builder().rating(ratingCreateRequestDto.getRating())
                .comment(ratingCreateRequestDto.getComment()).customer(customer).serviceProvider(serviceProvider)
                .createdDate(LocalDateTime.now()).updatedDate(LocalDateTime.now()).build();
        ratingRepository.save(rating);
        log.info("Rating with id: {} has been created for service provider with id: {} by customer with id: {}",
                rating.getId(), serviceProvider.getId(), customer.getId());
        return ResponseEntity.ok(new MessageWrapperDto(generalMessageAccessor.getMessage(null,
                ProjectConstants.RATING_CREATED)));
    }

    public Boolean isRatingExists(Customer customer, ServiceProvider serviceProvider) {
        return ratingRepository.existsByCustomerAndAndServiceProvider(customer, serviceProvider);
    }


    @Transactional
    public ResponseEntity<List<RatingDto>> getRating(Long id, String email) {
        final Customer customer = customerService.findCustomerByUserEmail(email);
        final ServiceProvider serviceProvider = serviceOfServiceProvider.findById(id);
        log.info("Customer with id {} is trying to get rating for service provider with id {}",
                customer.getId(), serviceProvider.getId());
        List<Rating> responseRatings = new ArrayList<>();
        Rating currentCustomerRating = null;
        for (Rating rating : serviceProvider.getRatings()) {
            if (rating.getCustomer().getId().equals(customer.getId())) {
                currentCustomerRating = rating;
            } else {
                responseRatings.add(rating);
            }
        }
        responseRatings.sort(Comparator.comparing(Rating::getUpdatedDate).reversed());
        if (currentCustomerRating != null)
            responseRatings.add(0, currentCustomerRating);
        log.info("fetched {} ratings of service provider with id {} for customer with id {}",
                responseRatings.size(), serviceProvider.getId(), customer.getId());
        return ResponseEntity.ok(RatingsMapper.INSTANCE.convertToRatingDtoList(responseRatings));
    }

    private Rating getRatingById(Long id) {
        return ratingRepository.findById(id).orElseThrow(() -> new CustomException(new ApiExceptionResponse(
                exceptionMessageAccessor.getMessage(null, ProjectConstants.RATING_NOT_FOUND, id),
                HttpStatus.NOT_FOUND, LocalDateTime.now())));
    }

    @Transactional
    public ResponseEntity<MessageWrapperDto> updateRating(String email, RatingCreateRequestDto ratingCreateRequestDto) {
        Rating rating = getRatingById(ratingCreateRequestDto.getId());
        Customer customer = customerService.findCustomerByUserEmail(email);
        log.info("Customer with id {} is trying to update rating with id {}",
                customer.getId(), ratingCreateRequestDto.getId());

        if (!rating.getCustomer().getId().equals(customer.getId())) {
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(null,
                    ProjectConstants.RATING_NOT_FOUND, ratingCreateRequestDto.getId()), HttpStatus.NOT_FOUND,
                    LocalDateTime.now()));
        }

        rating.setRating(ratingCreateRequestDto.getRating());
        rating.setComment(ratingCreateRequestDto.getComment());
        rating.setUpdatedDate(LocalDateTime.now());
        ratingRepository.save(rating);
        log.info("Rating with id: {} has been updated by customer with id: {}",
                rating.getId(), customer.getId());

        return ResponseEntity.ok(new MessageWrapperDto(generalMessageAccessor.getMessage(null,
                ProjectConstants.RATING_UPDATED)));
    }


    @Transactional
    public ResponseEntity<MessageWrapperDto> deleteRating(Long id, String email) {
        Rating rating = getRatingById(id);
        Customer customer = customerService.findCustomerByUserEmail(email);
        log.info("Customer with id {} is trying to delete rating with id {}",
                customer.getId(), id);
        if(!rating.getCustomer().getId().equals(customer.getId())){
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(null,
                    ProjectConstants.RATING_NOT_FOUND, id), HttpStatus.NOT_FOUND,
                    LocalDateTime.now()));
        }
        ratingRepository.delete(rating);
        log.info("Rating with id: {} has been deleted by customer with id: {}",
                rating.getId(), customer.getId());
        return ResponseEntity.ok(new MessageWrapperDto(generalMessageAccessor.getMessage(null,
                ProjectConstants.RATING_DELETED)));
    }
}
