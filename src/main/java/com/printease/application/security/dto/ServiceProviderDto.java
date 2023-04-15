package com.printease.application.security.dto;

import com.printease.application.model.Address;
import com.printease.application.model.AssociatedService;
import com.printease.application.model.Rating;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderDto {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String businessName;

    private String gstIn;

    private List<AssociatedServiceDto> associatedServices;

//    private List<Rating> ratings;

    private AddressDto address;
}
