package io.github.ftincdev.mt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.ftincdev.mt.config.ConfigParser;
import io.github.ftincdev.mt.config.MTConfig;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("MT Core started with {} args", args.length);

        try {
            MTConfig config = ConfigParser.parse("config.json");
            LOGGER.info("Configuration loaded");
        } catch (IOException e) {
            LOGGER.error("Failed to read config", e);
        }
    }
}
