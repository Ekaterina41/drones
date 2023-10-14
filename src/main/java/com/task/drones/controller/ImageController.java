package com.task.drones.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * REST controller for downloading images.
 */
@RequestMapping("/image")
public interface ImageController {

    /**
     * Download medication image.
     * @param fileName image name
     * @param request http request
     * @return image
     */
    @GetMapping("/download/{fileName}")
    ResponseEntity<Resource> downloadImage(@PathVariable String fileName,
                                           HttpServletRequest request);
}
