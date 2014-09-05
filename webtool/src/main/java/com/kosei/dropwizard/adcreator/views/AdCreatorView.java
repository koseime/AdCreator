package com.kosei.dropwizard.adcreator.views;

import com.kosei.dropwizard.adcreator.core.AdCreator;
import com.kosei.dropwizard.adcreator.core.Person;
import io.dropwizard.views.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by lanceriedel on 9/4/14.
 */
public class AdCreatorView extends View {
    private static final String template = "freemarker/adcreator.ftl";
    private final AdCreator adCreator;
    private final Collection<String> fonts;
    private final Collection<String> logos;
    private final Collection<String> products;


    public AdCreatorView(AdCreator adCreator, Collection<String> fonts, Collection<String> logos, Collection<String> products) {
        super(template);
        this.adCreator = adCreator;
        this.fonts = fonts;
        this.products = products;
        this.logos = logos;
    }

    public AdCreator getAdCreator() {
        return adCreator;
    }

    public List<String> getFonts() {
        return new ArrayList(fonts);
    }

    public List<String> getLogos() {
        return new ArrayList(logos);
    }

    public List<String> getProducts() {
        return new ArrayList(products);
    }
}
