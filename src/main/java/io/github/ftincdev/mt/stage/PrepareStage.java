package io.github.ftincdev.mt.stage;

import io.github.ftincdev.mt.config.MTConfig;
import io.github.ftincdev.mt.template.TemplateDownloader;
import io.github.ftincdev.mt.template.TemplateUnpacker;

import org.gradle.api.logging.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class PrepareStage {
    private final MTConfig config;
    private final Logger logger;

    public PrepareStage(MTConfig config, Logger logger) {
        this.config = config;
        this.logger = logger;
    }

    public void execute() throws Exception {
        Path cacheDir = Path.of(config.cacheDir);
        
        Path templateZip = cacheDir.resolve("template-" + config.templateVersion + ".zip");
        Path versionedTemplateDir = cacheDir.resolve("templates").resolve(config.templateVersion);
        Path outputDir = Path.of(config.outputDir);

        if (!Files.exists(versionedTemplateDir)) {
            logger.lifecycle("Template version {} not found in cache. Preparing...", config.templateVersion);
            
            if (!Files.exists(templateZip)) {
                logger.lifecycle("Downloading template archive...");
                new TemplateDownloader().download(config.getTemplateUrl(), templateZip);
            }

            logger.lifecycle("Unpacking archive to cache...");
            new TemplateUnpacker().unpack(templateZip, versionedTemplateDir);
        } else {
            logger.lifecycle("Found template version {} in cache.", config.templateVersion);
        }

        logger.lifecycle("Copying template to output directory...");
        copyDirectory(versionedTemplateDir, outputDir);
    }

    private void copyDirectory(Path source, Path target) throws IOException {
        Files.createDirectories(target);

        try (var stream = Files.walk(source)) {
            stream.forEach(path -> {
                try {
                    Path targetPath = target.resolve(source.relativize(path).toString());
                    if (Files.isDirectory(path)) {
                        Files.createDirectories(targetPath);
                    } else {
                        Files.createDirectories(targetPath.getParent());
                        Files.copy(path, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Failed to copy " + path, e);
                }
            });
        }
    }
}
