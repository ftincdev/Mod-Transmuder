package io.github.ftincdev.mt.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MTConfig {
    @JsonProperty("templateVersion")
    public String templateVersion;

    @JsonProperty("templateUrl")
    private String templateUrlRaw;

    @JsonIgnore
    public String getTemplateUrl() {
        if (templateUrlRaw == null) return null;
        return templateUrlRaw.replace("${version}", this.templateVersion);
    }

    @JsonProperty("outputDir")
    public String outputDir;

    @JsonProperty("inputDir")
    public String inputDir;

    @JsonProperty("cacheDir")
    public String cacheDir;

    // not implemented
    @JsonProperty("rewriteData")
    public boolean rewriteData;

    // not implemented
    @JsonProperty("stopIfFail")
    public boolean stopIfFail;
}
