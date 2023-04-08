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
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serviceName;
    private String description;
    @OneToMany
    private List<PrintingType> printingTypes;
    @OneToMany
    private List<PrintSide> printSides;
    @OneToMany
    private List<PaperSize> paperSizes;
    @OneToMany
    private List<PaperType> paperTypes;
    @OneToMany
    private List<Orientation> orientations;
}
