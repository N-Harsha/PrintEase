package com.printease.application.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrintService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serviceName;
    @Lob
    private String description;
    @ManyToMany
    private List<PrintType> printTypes;
    @ManyToMany
    private List<PrintSide> printSides;
    @ManyToMany
    private List<PaperSize> paperSizes;
    @ManyToMany
    private List<PaperType> paperTypes;
    @ManyToMany
    private List<Orientation> orientations;
    @ManyToMany
    private List<BindingType> bindingTypes;
}
