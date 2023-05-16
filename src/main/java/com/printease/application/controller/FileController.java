package com.printease.application.controller;

import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import com.printease.application.service.FileService;
import com.printease.application.utils.ExceptionMessageAccessor;
import com.printease.application.utils.ProjectConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    private final ExceptionMessageAccessor exceptionMessageAccessor;

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId, Principal principal) {
        try {
            return fileService.downloadFile(fileId, principal.getName());
        } catch (IOException e) {
            throw new CustomException(
                    new ApiExceptionResponse(
                            exceptionMessageAccessor.getMessage(null,
                                    ProjectConstants.FILE_CANNOT_BE_DOWNLOADED, fileId),
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            LocalDateTime.now()
                    )
            );
        }
        catch (CustomException e){
            throw new CustomException(e.getErrorResponse());
        }
    }


    @PostMapping("/pages")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<String> pagesInFile(@RequestBody MultipartFile file, Principal principal) {
            return fileService.pagesInFile(file, principal.getName());
    }
}
