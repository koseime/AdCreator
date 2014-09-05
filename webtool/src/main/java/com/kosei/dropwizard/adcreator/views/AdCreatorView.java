package com.kosei.dropwizard.adcreator.views;

import com.kosei.dropwizard.adcreator.core.AdCreator;
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
    private final Collection<String> sizes;
    private final Collection<String> weights;


    public String selectedDescriptionFont;
    public String selectedDescriptionFontWeight;
    public String selectedDescriptionFontSize;
    public String selectedHeaderFont;
    public String selectedHeaderFontWeight;
    public String selectedHeaderFontSize;
    public String selectedLogo;
    public String selectedProduct;

    public String headerFontColor;
    public String descriptionFontColor;



    public String descriptionText;
    public String headerText;

    public String backgroundColor;


    public AdCreatorView(AdCreator adCreator, Collection<String> fonts, Collection<String> logos, Collection<String> products,
                         Collection<String> sizes, Collection<String> weights
    ) {
        super(template);
        this.adCreator = adCreator;
        this.fonts = fonts;
        this.products = products;
        this.logos = logos;
        this.sizes = sizes;
        this.weights = weights;
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

    public Collection<String> getSizes() {
        return sizes;
    }

    public Collection<String> getWeights() {
        return weights;
    }

    public String selectedProduct(String val) {
        if (selectedProduct == null) return null;
        else if (selectedProduct.equals(val))
            return "selected";
        return null;
    }

    public String selectedLogo(String val) {
        if (selectedLogo == null) return null;
        else if (selectedLogo.equals(val))
            return "selected";
        return null;
    }

    public String selectedHeaderFont(String val) {
        if (selectedHeaderFont == null) return null;
        else if (selectedHeaderFont.equals(val))
            return "selected";
        return null;
    }

    public String selectedHeaderFontSize(String val) {
        if (selectedHeaderFontSize == null) return null;
        else if (selectedHeaderFontSize.equals(val))
            return "selected";
        return null;
    }

    public String selectedHeaderFontWeight(String val) {
        if (selectedHeaderFontWeight == null) return null;
        else if (selectedHeaderFontWeight.equals(val))
            return "selected";
        return null;
    }


    public String selectedDescriptionFont(String val) {
        if (selectedDescriptionFont == null) return null;
        else if (selectedDescriptionFont.equals(val))
            return "selected";
        return null;
    }

    public String selectedDescriptionFontSize(String val) {
        if (selectedDescriptionFontSize == null) return null;
        else if (selectedDescriptionFontSize.equals(val))
            return "selected";
        return null;
    }

    public String selectedDescriptionFontWeight(String val) {
        if (selectedDescriptionFontWeight == null) return null;
        else if (selectedDescriptionFontWeight.equals(val))
            return "selected";
        return null;
    }


    public String getDescriptionFontColor() {
        return descriptionFontColor;
    }

    public String getHeaderFontColor() {
        return headerFontColor;
    }


    public String getHeaderText() {
        return headerText;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public String getBackgroundColor() { return backgroundColor; }
}
