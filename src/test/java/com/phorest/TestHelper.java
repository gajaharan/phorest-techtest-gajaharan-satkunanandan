package com.phorest;

import org.springframework.core.io.FileSystemResource;

import java.nio.file.Path;

public class TestHelper {
    public static FileSystemResource getFile(String fileName) {
        return new FileSystemResource(Path.of("src", "test", "resources", "csv", fileName));
    }
}
