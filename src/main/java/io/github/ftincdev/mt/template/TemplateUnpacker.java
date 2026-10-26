package io.github.ftincdev.mt.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TemplateUnpacker {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateUnpacker.class);

    public void unpack(Path zipPath, Path targetDir) throws IOException {
    LOGGER.info("Unpacking template to: {}", targetDir.toAbsolutePath());
    Files.createDirectories(targetDir);

    try (InputStream is = Files.newInputStream(zipPath);
        ZipInputStream zis = new ZipInputStream(is)) {

            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                Path filePath = targetDir.resolve(entry.getName()).normalize();

                if (!filePath.startsWith(targetDir.normalize())) {
                    throw new IOException("Bad zip entry: " + entry.getName());
                }

                if (entry.isDirectory()) {
                    Files.createDirectories(filePath);
                } else {
                    Files.createDirectories(filePath.getParent());
                    Files.copy(zis, filePath);
                }
            }
        }
        
        LOGGER.info("Template unpacked successfully.");
    }
}
