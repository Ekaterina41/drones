package com.task.drones.service;

import com.task.drones.exception.FileStorageException;
import com.task.drones.property.ImageStorageProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    private final Path fileStorageLocation;
    @Autowired
    public ImageStorageServiceImpl(ImageStorageProperties imageStorageProperties) {

        String uploadUrl = imageStorageProperties.getUploadUrl();
        this.fileStorageLocation = Paths.get(uploadUrl).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new FileStorageException("Failed to create images directory: " + uploadUrl);
        }
    }

    @Override
    public String saveImage(MultipartFile image) {

        String fileName = StringUtils.cleanPath(image.getOriginalFilename());

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException(String.format(
                    "Could not store image %s. Reason: %s", fileName,
                    e.getMessage()));
        }

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("image/download/")
                .path(fileName)
                .toUriString();
    }

    @Override
    public Resource downloadFileAsResource(String fileName, HttpServletRequest request) { // todo unused
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileStorageException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileStorageException("Malformed URL for file: " + fileName);
        }
    }
}
