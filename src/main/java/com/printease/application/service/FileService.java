package com.printease.application.service;

import com.printease.application.model.FileDB;
import com.printease.application.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

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
}
