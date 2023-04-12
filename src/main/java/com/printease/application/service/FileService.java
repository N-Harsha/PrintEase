package com.printease.application.service;

import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import com.printease.application.model.FileDB;
import com.printease.application.model.Order;
import com.printease.application.repository.FileRepository;
import com.printease.application.utils.ExceptionMessageAccessor;
import com.printease.application.utils.ProjectConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final OrderService orderService;
    private final ExceptionMessageAccessor exceptionMessageAccessor;

    public FileDB saveCompressedFileToDatabase(MultipartFile file) throws IOException {

        // Compress the file
        byte[] compressedBytes = compress(file.getBytes());


        // Create a File object with the compressed bytes
        FileDB compressedFile = FileDB.builder().
                name(file.getOriginalFilename()).
                type(file.getContentType()).
                data(compressedBytes).
                build();

        return fileRepository.save(compressedFile);
    }

    private byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DeflaterOutputStream dos = new DeflaterOutputStream(baos, new Deflater(Deflater.BEST_COMPRESSION));
        dos.write(data);
        dos.close();
        return baos.toByteArray();
    }

    private byte[] decompress(byte[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(data));
        byte[] buffer = new byte[1024];
        int length;
        while ((length = iis.read(buffer)) > 0) {
            baos.write(buffer, 0, length);
        }
        baos.close();
        return baos.toByteArray();
    }

    public ResponseEntity<byte[]> downloadFile(Long fileId, String email) throws IOException {
        FileDB file = fileRepository.findById(fileId).orElseThrow(() -> new CustomException(
                new ApiExceptionResponse(
                        exceptionMessageAccessor.getMessage(null, ProjectConstants.FILE_NOT_FOUND, fileId),
                        HttpStatus.BAD_REQUEST, LocalDateTime.now()
                )
        ));

        Order order = orderService.getOrderByFileId(file.getId());
        //future only the service provider can only download the file if the order is the accepted or above stages.
        if (!order.getCustomer().getEmail().equalsIgnoreCase(email) &&
                !order.getAssociatedService().getServiceProvider()
                        .getEmail().equalsIgnoreCase(email)) {
            throw new CustomException(
                    new ApiExceptionResponse(
                            exceptionMessageAccessor.getMessage(null, ProjectConstants.DOWNLOAD_NOT_AUTHORIZED, fileId),
                            HttpStatus.FORBIDDEN, LocalDateTime.now()
                    )
            );
        }

        byte[] fileBytes = decompress(file.getData());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(file.getType()));
        headers.setContentDisposition(ContentDisposition.attachment().filename(file.getName()).build());

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }
}
