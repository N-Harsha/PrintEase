package com.printease.application.bootstrap;

import com.printease.application.model.*;
import com.printease.application.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BootstrapRunner implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;
    private final OrientationRepository orientationRepository;
    private final PaperSizeRepository paperSizeRepository;
    private final PaperTypeRepository paperTypeRepository;
    private final PrintSideRepository printSideRepository;
    @Override
    public void run(String... args) throws Exception {
        if(userRoleRepository.count()==0){
            userRoleRepository.save(UserRole.builder().role("ROLE_ADMIN").build());
            userRoleRepository.save(UserRole.builder().role("ROLE_CUSTOMER").build());
            userRoleRepository.save(UserRole.builder().role("ROLE_SERVICE_PROVIDER").build());
        }
        if(orientationRepository.count()==0){
            orientationRepository.save(Orientation.builder().orientation("Portrait").build());
            orientationRepository.save(Orientation.builder().orientation("Landscape").build());
        }
        if(paperSizeRepository.count()==0){
            paperSizeRepository.save(PaperSize.builder().size("A4").build());
            paperSizeRepository.save(PaperSize.builder().size("A3").build());
            paperSizeRepository.save(PaperSize.builder().size("A2").build());
            paperSizeRepository.save(PaperSize.builder().size("A1").build());
            paperSizeRepository.save(PaperSize.builder().size("A0").build());
        }
        if(paperTypeRepository.count()==0){
            paperTypeRepository.save(PaperType.builder().type("Glossy").build());
            paperTypeRepository.save(PaperType.builder().type("Matte").build());
            paperTypeRepository.save(PaperType.builder().type("Satin").build());
            paperTypeRepository.save(PaperType.builder().type("Lustre").build());
            paperTypeRepository.save(PaperType.builder().type("Photo").build());
        }
        if(printSideRepository.count()==0){
            printSideRepository.save(PrintSide.builder().printSideType("Single").build());
            printSideRepository.save(PrintSide.builder().printSideType("Double").build());
        }
    }
}
