package io.github.ftincdev.mt.stage;

import io.github.ftincdev.mt.config.MTConfig;
import io.github.ftincdev.mttransformer.MainTransformer;

import org.gradle.api.logging.Logger;

import java.nio.file.Path;

public class TransformerStage {
    private final MTConfig config;
    private final Logger logger;

    public TransformerStage(MTConfig config, Logger logger) {
        this.config = config;
        this.logger = logger;
    }

    public void execute() throws Exception {
        Path outputDir = Path.of(config.outputDir);
        logger.lifecycle("Starting transformation stage...");
        
        MainTransformer transformer = new MainTransformer();
        transformer.transform(outputDir);
        
        logger.lifecycle("Transformation stage finished.");
    }
}
