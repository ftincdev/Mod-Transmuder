package io.github.ftincdev.mt;

import io.github.ftincdev.mt.config.ConfigParser;
import io.github.ftincdev.mt.config.MTConfig;
import io.github.ftincdev.mt.stage.PrepareStage;
import io.github.ftincdev.mt.stage.TransformerStage;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MTPlugin implements Plugin<Project> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MTPlugin.class);

    @Override
    public void apply(Project project) {
        File configFile = new File(project.getProjectDir(), "config.json");
        File projectDir = project.getProjectDir();

        project.getTasks().register("mtTransform", task -> {
            task.setGroup("mt");
            task.setDescription("Transforms 1.7.10 mod sources to 1.21.1");
            
            task.doLast(t -> {
                try {
                    t.getLogger().lifecycle("Transmudation started");
                    
                    if (!configFile.exists()) {
                        throw new RuntimeException("config.json not found at: " + configFile.getAbsolutePath());
                    }

                    MTConfig config = ConfigParser.parse(configFile.getAbsolutePath());
                    
                    if (!Path.of(config.outputDir).isAbsolute()) {
                        config.outputDir = new File(projectDir, config.outputDir).getAbsolutePath();
                    }
                    if (config.inputDir != null && !Path.of(config.inputDir).isAbsolute()) {
                        config.inputDir = new File(projectDir, config.inputDir).getAbsolutePath();
                    }
                    if (config.cacheDir != null && !Path.of(config.cacheDir).isAbsolute()) {
                        config.cacheDir = new File(projectDir, config.cacheDir).getAbsolutePath();
                    }

                    org.gradle.api.logging.Logger gradleLogger = t.getLogger();
                
                    new PrepareStage(config, gradleLogger).execute();
                    new TransformerStage(config, gradleLogger).execute();

                } catch (Exception e) {
                    LOGGER.error("Pipeline failed", e);
                    throw new RuntimeException("MT Pipeline failed", e);
                }
            });
        });

        project.getTasks().register("mtBuild", task -> {
            task.setGroup("mt");
            task.setDescription("Builds the transmuted mod");
            task.dependsOn("mtTransform");

            task.doLast(t -> {
                try {
                    String outputPath = getOutputPath(configFile, projectDir);
                    LOGGER.info("Building transmuted mod in: {}", outputPath);
                    runGradleCommand("build", outputPath, t);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to build", e);
                }
            });
        });

        project.getTasks().register("mtRunClient", task -> {
            task.setGroup("mt");
            task.setDescription("Runs the client with the transmuted mod");
            task.dependsOn("mtBuild");

            task.doLast(t -> {
                try {
                    String outputPath = getOutputPath(configFile, projectDir);
                    LOGGER.info("Running client from: {}", outputPath);
                    runGradleCommand(":fabric:runClient", outputPath, t);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to run client", e);
                }
            });
        });
    }

    private String getOutputPath(File configFile, File projectDir) throws Exception {
        MTConfig config = ConfigParser.parse(configFile.getAbsolutePath());
        return Path.of(config.outputDir).isAbsolute() 
            ? config.outputDir 
            : new File(projectDir, config.outputDir).getAbsolutePath();
    }

    private void runGradleCommand(String command, String workingDir, Task task) throws Exception {
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        
        ProcessBuilder pb;
        if (isWindows) {
            pb = new ProcessBuilder("cmd", "/c", "gradlew.bat", command, "--console=plain", "--build-cache");
        } else {
            pb = new ProcessBuilder("./gradlew", command, "--console=plain", "--build-cache");
        }
        
        pb.directory(new File(workingDir));
        pb.redirectErrorStream(true);

        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                task.getLogger().lifecycle("[MT] " + line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Process exited with code " + exitCode);
        }
    }
}
