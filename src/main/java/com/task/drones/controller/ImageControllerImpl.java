package com.task.drones.controller;

import com.task.drones.service.ImageStorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * ImageController implementation for downloading images from filesystem.
 */
@RestController
@RequiredArgsConstructor
public class ImageControllerImpl implements ImageController {

    private final ImageStorageService imageStorageService;

    @Override
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String fileName,
                                                  HttpServletRequest request) {
        Resource resource = imageStorageService.downloadFileAsResource(fileName, request);

        // Determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(
                    resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // Default content type
            contentType = MediaType.IMAGE_PNG_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
