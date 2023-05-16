package com.printease.application.repository;

import com.printease.application.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface AssociatedServiceRepository extends CrudRepository<AssociatedService, Long> {
    List<AssociatedService> findAllByServiceProvider(ServiceProvider serviceProvider);

    List<AssociatedService> findAllByServiceProviderAndService(ServiceProvider serviceProvider, PrintService service);

    Boolean existsByIdAndServiceProvider(Long id, ServiceProvider serviceProvider);

//    @Query("SELECT a FROM AssociatedService a WHERE " +
//            "(?1=null OR a.service=null OR a.service.id = ?1) AND " +
//            "(?2=null OR a.orientation=null OR a.orientation.id = ?2) AND " +
//            "(?3=null OR a.paperSize=null OR a.paperSize.id = ?3) AND " +
//            "(?4=null OR a.paperType=null OR a.paperType.id = ?4) AND " +
//            "(?5=null OR a.printSide=null OR a.printSide.id = ?5) AND " +
//            "(?6=null OR a.printType=null OR a.printType.id = ?6) AND " +
//            "(?7=null OR a.bindingType=null OR a.bindingType.id = ?7) " +
//            "ORDER BY distance(a.serviceProvider.address.latitude, a.serviceProvider.address.longitude, ?8, ?9) ASC, a.price ASC")
//
//    List<AssociatedService> findAllByAssociatedServiceConfig(Long printServiceId, Long orientationId,
//                                                             Long paperSizeId, Long paperTypeId, Long printSideId,
//                                                             Long printTypeId, Long bindingTypeId,
//                                                             Double latitude, Double longitude);

//    default Double haversine(Double lat1, Double lon1, Double lat2, Double lon2) {
//        double earthRadius = 6371; // in kilometers
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double distance = earthRadius * c;
//        System.out.println(distance);
//        return distance;
//    }
}
