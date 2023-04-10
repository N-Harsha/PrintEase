package com.printease.application.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ASSOCIATED_SERVICES")
public class AssociatedService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private PrintService service;

    private Float price;

//    @OneToMany(mappedBy = "associatedService")
//    private List<Offer> offerList;

    @ManyToOne
    @JoinColumn(name = "service_provider_id",nullable = false)
    private ServiceProvider serviceProvider;

    @OneToOne
    private Orientation  orientation;
    @OneToOne
    private PaperSize paperSize;
    @OneToOne
    private PaperType paperType;
    @OneToOne
    private PrintSide printSide;
    @OneToOne
    private PrintType printType;
    @OneToOne
    private BindingType bindingType;

}
