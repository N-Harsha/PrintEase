//package com.printease.application.bootstrap;
//
//import com.printease.application.model.*;
//import com.printease.application.repository.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class BootstrapRunner implements CommandLineRunner {
//
//    private final UserRoleRepository userRoleRepository;
//    private final OrientationRepository orientationRepository;
//    private final PaperSizeRepository paperSizeRepository;
//    private final PaperTypeRepository paperTypeRepository;
//    private final PrintSideRepository printSideRepository;
//    private final PrintTypeRepository printTypeRepository;
//    private final PrintServiceRepository printServiceRepository;
//    private final BindingTypeRepository bindingTypeRepository;
//    private final OrderStatusRepository orderStatusRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        UserRole roleAdmin = userRoleRepository.save(UserRole.builder().role("ROLE_ADMIN").build());
//        UserRole roleCustomer = userRoleRepository.save(UserRole.builder().role("ROLE_CUSTOMER").build());
//        UserRole roleServiceProvider = userRoleRepository.save(UserRole.builder().role("ROLE_SERVICE_PROVIDER").build());
//
//
//        Orientation portraitOrientation = orientationRepository.save(Orientation.builder().orientation("Portrait").build());
//        Orientation landscapeOrientation = orientationRepository.save(Orientation.builder().orientation("Landscape").build());
//
////        if (paperSizeRepository.count() == 0) {
//        PaperSize A1 = paperSizeRepository.save(PaperSize.builder().size("A1").build());
//        PaperSize A2 = paperSizeRepository.save(PaperSize.builder().size("A2").build());
//        PaperSize A3 = paperSizeRepository.save(PaperSize.builder().size("A3").build());
//        PaperSize A4 = paperSizeRepository.save(PaperSize.builder().size("A4").build());
//        PaperSize A5 = paperSizeRepository.save(PaperSize.builder().size("A5").build());
//        PaperSize A6 = paperSizeRepository.save(PaperSize.builder().size("A6").build());
////        }
//
////        if (paperTypeRepository.count() == 0) {
//        PaperType glossyPaperType = paperTypeRepository.save(PaperType.builder().type("Glossy").build());
//        PaperType mattePaperType = paperTypeRepository.save(PaperType.builder().type("Matte").build());
//        PaperType satinPaperType = paperTypeRepository.save(PaperType.builder().type("Satin").build());
//        PaperType lustrePaperType = paperTypeRepository.save(PaperType.builder().type("Lustre").build());
//        PaperType photoPaperType = paperTypeRepository.save(PaperType.builder().type("Photo").build());
////        }
////        if (printSideRepository.count() == 0) {
//        PrintSide singlePrintSide = printSideRepository.save(PrintSide.builder().printSideType("Single").build());
//        PrintSide doublePrintSide = printSideRepository.save(PrintSide.builder().printSideType("Double").build());
////        }
////        if (printTypeRepository.count() == 0) {
//        PrintType blackAndWhitePrintType = printTypeRepository.save(PrintType.builder().type("Black and White").build());
//        PrintType colorPrintType = printTypeRepository.save(PrintType.builder().type("Color").build());
////        }
//
//        BindingType thermalBinding = bindingTypeRepository.save(BindingType.builder()
//                .name("Thermal binding")
//                .description("A quick and easy method that uses heat to bind the pages together without the need for punching holes.")
//                .build());
//
//        BindingType perfectBinding = bindingTypeRepository.save(BindingType.builder()
//                .name("Perfect binding")
//                .description("A popular method for binding reports and documents that uses an adhesive to attach a paper cover to the spine of the assembled pages.")
//                .build());
//
//        BindingType postBinding = bindingTypeRepository.save(BindingType.builder()
//                .name("Post binding")
//                .description("A method that uses chrome or brass rivets to fix the document together.")
//                .build());
//
//        BindingType saddleStitchedBinding = bindingTypeRepository.save(BindingType.builder()
//                .name("Saddle-stitched binding")
//                .description("A popular method for smaller documents such as newsletters and calendars that uses staples to hold the folded sheets together along the spine.")
//                .build());
//
//        BindingType wireBinding = bindingTypeRepository.save(BindingType.builder()
//                .name("Wire binding")
//                .description("A method that uses a C-shaped wire spine to hold punched sheets of paper together by looping through punched holes in the paper.")
//                .build());
//
//        BindingType combBinding = bindingTypeRepository.save(BindingType.builder()
//                .name("Comb binding")
//                .description("A similar method to wire binding that uses round plastic spines through rectangular punched holes in the paper.")
//                .build());
//
//
//        PrintService documentPrinting = printServiceRepository.save(PrintService.builder()
//                .serviceName("Document Printing")
//                .description("Document printing is the process of creating a physical copy of a digital document." +
//                        " This can be done using a printer that is connected to your computer or other device." +
//                        " You can choose to print your document in black and white or color, depending on your" +
//                        " needs and the capabilities of your printer. There are also many online printing " +
//                        "services that offer high-quality document printing and binding options. You can al")
//                        .paperSizes(List.of(A4,A3,A2))
//                        .printSides(List.of(singlePrintSide,doublePrintSide))
//                        .orientations(List.of(portraitOrientation,landscapeOrientation))
//                        .printTypes(List.of(blackAndWhitePrintType,colorPrintType))
//                        .paperTypes(List.of(glossyPaperType,mattePaperType))
//                .build());
//        PrintService documentBinding = printServiceRepository.save(PrintService.builder()
//                        .serviceName("Document Binding")
//                        .description("Document binding is the process of securing loose pages together to create a " +
//                                "book or booklet. There are several different methods of binding documents, each with" +
//                                " its own advantages and uses. Some common binding methods include thermal binding," +
//                                " perfect binding, post binding, saddle-stitched binding, wire binding, and comb" +
//                                " binding. The method you choose will depend on the size and purpose of your document," +
//                                " as well as your personal preferences. Binding can help protect your document and" +
//                                " keep it in the exact order it was printed in.")
//                        .bindingTypes(List.of(thermalBinding,perfectBinding,postBinding,saddleStitchedBinding,wireBinding,combBinding))
//                .build());
//        PrintService posterPrinting = printServiceRepository.save(PrintService.builder()
//                        .serviceName("Poster Printing")
//                        .description("! Poster printing is the process of creating large-format prints that " +
//                                "are designed to be displayed on walls, windows, notice boards, or other surfaces." +
//                                " Posters can be used to advertise your business, promote an event, or convey" +
//                                " information to a wide audience. They come in a variety of sizes and can be printed" +
//                                " on different types of paper stock, including glossy and matte finishes. You can " +
//                                "create personalized posters by choosing from pre-designed templates or uploading " +
//                                "your own design. Poster printing is a great way to grab attention and get your " +
//                                "message across to your customers")
//                        .paperSizes(List.of(A5,A6))
//                        .orientations(List.of(portraitOrientation,landscapeOrientation))
//                        .printTypes(List.of(blackAndWhitePrintType,colorPrintType))
//                        .paperTypes(List.of(glossyPaperType,mattePaperType,satinPaperType,lustrePaperType,photoPaperType))
//                .build());
//
//        OrderStatus pendingStatus = orderStatusRepository.save(OrderStatus.builder().status("Pending").build());
//        OrderStatus acceptedStatus = orderStatusRepository.save(OrderStatus.builder().status("Accepted").build());
//        OrderStatus rejectedStatus = orderStatusRepository.save(OrderStatus.builder().status("Rejected").build());
//        OrderStatus inProgressStatus = orderStatusRepository.save(OrderStatus.builder().status("In Progress").build());
//        OrderStatus completedStatus = orderStatusRepository.save(OrderStatus.builder().status("Completed").build());
//        OrderStatus cancelledStatus = orderStatusRepository.save(OrderStatus.builder().status("Cancelled").build());
//
////        orderStatusRepository.save()
//    }
//}
