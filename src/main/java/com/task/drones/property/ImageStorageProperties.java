package com.task.drones.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for image storage.
 */
@ConfigurationProperties(prefix = "file")
@Getter
@Setter
public class ImageStorageProperties {
    private String uploadUrl;
}
