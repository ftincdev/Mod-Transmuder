package io.github.ftincdev.mt.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class TemplateDownloader {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateDownloader.class);

    public void download(String url, Path targetPath) throws IOException, InterruptedException {
        LOGGER.info("Downloading template from: {}", url);

        if (targetPath.getParent() != null) {
            Files.createDirectories(targetPath.getParent());
        }

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to download template. HTTP status: " + response.statusCode());
        }

        try (InputStream in = response.body()) {
            Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }

        LOGGER.info("Download complete: {}", targetPath.toAbsolutePath());
    }
}
