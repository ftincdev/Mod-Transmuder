package io.github.ftincdev.mt.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MTConfig {
    @JsonProperty("templateUrl")
    public String templateUrl;

    @JsonProperty("outputDir")
    public String outputDir;

    @JsonProperty("inputDir")
    public String inputDir;

    @JsonProperty("cacheDir")
    public String cacheDir;

    @JsonProperty("rewriteData")
    public boolean rewriteData;

    @JsonProperty("stopIfFail")
    public boolean stopIfFail;
}
