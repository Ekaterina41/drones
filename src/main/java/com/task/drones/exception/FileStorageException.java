package com.task.drones.exception;


/**
 * Exception that thrown when error occured while storing a file.
 */
public class FileStorageException extends RuntimeException {
    public FileStorageException(String s) {
        super(s);
    }
}
