package com.printease.application.security.utils;

import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class DocumentUtils {

    public  int getPageCount(MultipartFile file)throws CustomException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        switch (fileExtension.toLowerCase()) {
            case "pdf":
                return getPDFPageCount(file);
            case "ppt":
            case "pptx":
                return getPPTPageCount(file);
            case "docx":
                return getDocxPageCount(file);
            case "xls":
            case "xlsx":
                return getExcelPageCount(file);
            case "jpeg":
            case "jpg":
            case "png":
                return getImagePageCount(file);
            case "txt":
                return getTextPageCount(file);
            default:
                throw new CustomException(new ApiExceptionResponse("Invalid file format: " + fileExtension,  HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
    }

    private static int getPDFPageCount(MultipartFile file){
        try (PDDocument document = PDDocument.load(file.getBytes())) {
            return document.getNumberOfPages();
        }
        catch (IOException e) {
            throw new CustomException(new ApiExceptionResponse("Error while reading PDF file", HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now()));
        }
    }

    private static int getPPTPageCount(MultipartFile file) {
        try (XMLSlideShow document = new XMLSlideShow(file.getInputStream())) {
            return document.getSlides().size();
        }
        catch (IOException e) {
            throw new CustomException(new ApiExceptionResponse("Error while reading PPT file",  HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now()));
        }

    }

    private static int getDocxPageCount(MultipartFile file)  {
        try (XWPFDocument document = new XWPFDocument(file.getInputStream())) {
            return document.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
        }
        catch (IOException e) {
            throw new CustomException(new ApiExceptionResponse("Error while reading DOCX file",  HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now()));
        }
    }

    private static int getExcelPageCount(MultipartFile file) {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            return workbook.getNumberOfSheets();
        }
        catch (IOException e) {
            throw new CustomException(new ApiExceptionResponse("Error while reading Excel file",  HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now()));
        }
    }

    private static int getImagePageCount(MultipartFile file) {
        try {
        BufferedImage image = ImageIO.read(file.getInputStream());
        return image != null ? 1 : 0;
        }
        catch (IOException e) {
            throw new CustomException(new ApiExceptionResponse("Error while reading image file",  HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now()));
        }
    }

    private static int getTextPageCount(MultipartFile file) {
        // Read the file and count the number of lines
        try {
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            int count = 0;
            while ((bytesRead = file.getInputStream().read(buffer)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    if (buffer[i] == '\n') {
                        count++;
                    }
                }
            }
            return count;
        }
        catch (IOException e) {
            throw new CustomException(new ApiExceptionResponse("Error while reading text file",  HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now()));
        }
    }

    private static String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf(".");
        if (dotIndex > 0) {
            return filePath.substring(dotIndex + 1);
        }
        return "";
    }
}
