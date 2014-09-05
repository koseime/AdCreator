package com.kosei.dropwizard.adcreator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kosei.dropwizard.adcreator.core.Template;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class AdCreatorConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";


    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public Template buildTemplate() {
        return new Template(template, defaultName);
    }


    @NotEmpty
    private String productImageDirectory;

    public String getProductImageDirectory() {
        return productImageDirectory;
    }

    public void setProductImageDirectory(String productImageDirectory) {
        this.productImageDirectory = productImageDirectory;
    }

    @NotEmpty
    private String logoImageDirectory;

    public String getLogoImageDirectory() {
        return logoImageDirectory;
    }

    public void setLogoImageDirectory(String logoImageDirectory) {
        this.logoImageDirectory = logoImageDirectory;
    }

    @NotEmpty
    private String fontDirectory;

    public String getFontDirectory() {
        return fontDirectory;
    }

    public void setFontDirectory(String fontDirectory) {
        this.fontDirectory = fontDirectory;
    }
}
