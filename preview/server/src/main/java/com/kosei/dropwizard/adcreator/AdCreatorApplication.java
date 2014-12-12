package com.kosei.dropwizard.adcreator;

import com.kosei.dropwizard.adcreator.core.AdPreviewCreatorClient;
import com.kosei.dropwizard.adcreator.core.CreatedImageCache;
import com.kosei.dropwizard.adcreator.core.PreCannedImagesAndFonts;
import com.kosei.dropwizard.adcreator.core.Template;
import com.kosei.dropwizard.adcreator.core.service.PreviewService;
import com.kosei.dropwizard.adcreator.health.TemplateHealthCheck;
import com.kosei.dropwizard.adcreator.resources.AdCreatorResource;
import com.kosei.dropwizard.adcreator.resources.PersonResource;
import com.kosei.dropwizard.adcreator.resources.PreviewResource;
import com.kosei.dropwizard.adcreator.resources.ViewResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import resources.ResourceConstants;

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
      environment.jersey().setUrlPattern(ResourceConstants.CONTEXT_PATH + "/*");
      AdPreviewCreatorClient client = new AdPreviewCreatorClient();
      final Template template = configuration.buildTemplate();
      CreatedImageCache cache = new CreatedImageCache();
      PreviewService previewService = new PreviewService(client, cache);

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
      environment.jersey()
          .register(new PreviewResource(preCannedImagesAndFonts, previewService));
    }
}
