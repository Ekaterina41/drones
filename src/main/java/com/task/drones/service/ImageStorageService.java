package com.task.drones.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service for storing images in the filesystem.
 */
public interface ImageStorageService {
    /**
     * Save image to the filesystem and return download uri.
     * @param image image file
     * @return download uri of saved image.
     */
    String saveImage(MultipartFile image);

    /**
     * Resource for medication images download.
     * @param fileName image file name
     * @return resource
     */
    Resource downloadFileAsResource(String fileName);
}
