package com.printease.application.service;

import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import com.printease.application.model.Customer;
import com.printease.application.model.Rating;
import com.printease.application.model.ServiceProvider;
import com.printease.application.repository.RatingRepository;
import com.printease.application.security.dto.MessageWrapperDto;
import com.printease.application.security.dto.RatingCreateRequestDto;
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
        if(isRatingExists(customer, serviceProvider)) {
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(null,
                    ProjectConstants.RATING_ALREADY_GIVEN,serviceProvider.getId()), HttpStatus.BAD_REQUEST,
                    LocalDateTime.now()));
        }
        log.info("Customer with id: {} has not rated the service provider with id: {} yet.",customer.getId(),
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


}
