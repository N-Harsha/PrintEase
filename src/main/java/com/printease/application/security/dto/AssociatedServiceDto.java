package com.printease.application.security.dto;

import com.printease.application.model.*;
import lombok.*;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssociatedServiceDto {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Orientation  orientation;

    private PaperSize paperSize;

    private PaperType paperType;

    private PrintSide printSide;

    private PrintType printType;

    private BindingType bindingType;

    @NotNull
    private Float price;
}
