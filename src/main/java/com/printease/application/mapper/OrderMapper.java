package com.printease.application.mapper;

import com.printease.application.model.AssociatedService;
import com.printease.application.model.Order;
import com.printease.application.dto.AssociatedServiceDto;
import com.printease.application.dto.OrderDtoCustomer;
import com.printease.application.dto.OrderDtoServiceProvider;
import com.printease.application.utils.ProjectConstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "serviceProviderBusinessName", source = "associatedService.serviceProvider.businessName")
    @Mapping(target = "orderStatus", source = "orderStatus.status")
    @Mapping(target = "serviceName", source = "associatedService.service.serviceName")
    @Mapping(target = "fileDownloadableUrl", source = "order", qualifiedByName = "fileDownloadableUrlGenerator")
    @Mapping(target = "associatedService", source = "associatedService", qualifiedByName = "convertToAssociatedServiceDto")
    OrderDtoCustomer convertToOrderDtoCustomer(Order order);

    @Mapping(target = "customerName", source = "customer.name")
    @Mapping(target = "orderStatus", source = "orderStatus.status")
    @Mapping(target = "serviceName", source = "associatedService.service.serviceName")
    @Mapping(target = "fileDownloadableUrl", source = "order", qualifiedByName = "fileDownloadableUrlGenerator")
    @Mapping(target = "associatedService", source = "associatedService", qualifiedByName = "convertToAssociatedServiceDto")
    OrderDtoServiceProvider convertToOrderDtoServiceProvider(Order order);

    @Named("fileDownloadableUrlGenerator")
    default String fileDownloadableUrlGenerator(Order order) {
        return ProjectConstants.BASE_URL + "/api/v1/files/download/" + order.getFile().getId();
    }
    @Named("convertToAssociatedServiceDto")
    default AssociatedServiceDto convertToAssociatedServiceDto(AssociatedService associatedService){
        return AssociatedServiceMapper.INSTANCE.convertToAssociatedServiceDto(associatedService);
    }

}
