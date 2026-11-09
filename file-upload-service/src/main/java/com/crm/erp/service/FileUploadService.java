package com.crm.erp.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.crm.erp.enumeration.BucketName;

import com.crm.erp.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.*;

import static org.springframework.http.MediaType.*;

@Service
@Slf4j
public class FileUploadService {

    private final AmazonS3 s3;
    private final FileStoreService fileStoreService;

    @Autowired
    public FileUploadService(AmazonS3 s3, FileStoreService fileStoreService) {
        this.s3 = s3;
        this.fileStoreService = fileStoreService;
    }

    @Value("${application.bucket.name}")
    private String bucketName;

    public void uploadProductFile(String id, MultipartFile file) {

        // check image is not empty
        if (file.isEmpty())
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");

        // If file is an image
        if (!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_GIF_VALUE, IMAGE_PNG_VALUE).contains(file.getContentType()))
            throw new IllegalStateException("File Must be Image [" + file.getContentType() + "]");

        // The product is exists in our Database
        //Product product = getProductImageOrThrow(id);

        // Grab some metadata from file if any
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        // Store the image in S3 and update database (userProfileImageLink) with S3
        // image link
        String path = String.format("%s/%s", BucketName.PRODUCT.getBucketName(), id);
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStoreService.save(path, filename, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("file to Save " + e.getMessage());
        }
    }


    public String uploadBase64(String id, String directoryName, String data) {
        try {

            if (data.isEmpty())
                throw new IllegalStateException("Cannot upload empty file");

            String extension = FileUtil.getImageExtension(data);

            String path = directoryName + "/";
            String filename = id + "." + extension;
            String filepath = path + filename;

            byte[] bytes = Base64.decodeBase64(data.split(",")[1].getBytes());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

            String contentType = URLConnection.getFileNameMap().getContentTypeFor(filename);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(bytes.length);
            metadata.setCacheControl("public, max-age=31536000");


            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filepath, inputStream, metadata);
            s3.putObject(putObjectRequest);

            return filepath;

        } catch (Exception e) {
            System.out.println("Failed to Save");
            e.printStackTrace();
        }
        return null;
    }
}
