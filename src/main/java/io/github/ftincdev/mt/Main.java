package io.github.ftincdev.mt;

import io.github.ftincdev.mt.config.ConfigParser;
import io.github.ftincdev.mt.config.MTConfig;
import io.github.ftincdev.mt.stage.PrepareStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // args not implemented yet
        LOGGER.info("MT Core started with {} args", args.length);

        try {
            MTConfig config = ConfigParser.parse("config.json");
            LOGGER.info("Configuration loaded");

            new PrepareStage(config).execute();

        } catch (Exception e) {
            LOGGER.error("Pipelsine failed", e);
            System.exit(1);
        }
    }
}
