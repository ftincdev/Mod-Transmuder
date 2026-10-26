package io.github.ftincdev.mt.stage;

import io.github.ftincdev.mt.config.MTConfig;
import io.github.ftincdev.mt.template.TemplateDownloader;
import io.github.ftincdev.mt.template.TemplateUnpacker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;

public class PrepareStage {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrepareStage.class);
    private final MTConfig config;

    public PrepareStage(MTConfig config) {
        this.config = config;
    }

    public void execute() throws Exception {
        Path templateZip = Path.of(config.cacheDir, "template.zip");

        if (!Files.exists(templateZip)) {
            LOGGER.info("Template not found in cache. Starting download...");
            new TemplateDownloader().download(config.templateUrl, templateZip);
        } else {
            LOGGER.info("Template already exists in cache: {}", templateZip);
        }

        LOGGER.info("Unpacking template to output directory...");
        new TemplateUnpacker().unpack(templateZip, Path.of(config.outputDir));
    }
}
