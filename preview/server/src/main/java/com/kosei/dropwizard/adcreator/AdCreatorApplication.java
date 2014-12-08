package com.kosei.dropwizard.adcreator;

import com.kosei.dropwizard.adcreator.core.*;
import com.kosei.dropwizard.adcreator.health.TemplateHealthCheck;
import com.kosei.dropwizard.adcreator.resources.*;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import java.io.IOException;

public class AdCreatorApplication extends Application<AdCreatorConfiguration> {
    public static void main(String[] args) throws Exception {
        new AdCreatorApplication().run(args);
    }


    @Override
    public String getName() {
        return "ad-creator";
    }

    @Override
    public void initialize(Bootstrap<AdCreatorConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new ViewBundle());
    }

    @Override
    public void run(AdCreatorConfiguration configuration,
                    Environment environment) throws ClassNotFoundException, IOException {

        AdPreviewCreatorClient client = new AdPreviewCreatorClient();
        final Template template = configuration.buildTemplate();
        CreatedImageCache cache = new CreatedImageCache();

        PreCannedImagesAndFonts preCannedImagesAndFonts = new PreCannedImagesAndFonts(
                configuration.getFontDirectory(),
                configuration.getLogoImageDirectory(),
                configuration.getProductImageDirectory(),
                configuration.getCallToActionImageDirectory(),
                configuration.getTemplateDirectory());

        environment.healthChecks().register("template", new TemplateHealthCheck(template));

        environment.jersey().register(new ViewResource());
        environment.jersey().register(new PersonResource());
        environment.jersey().register(new AdCreatorResource(cache, client, preCannedImagesAndFonts));
    }
}
