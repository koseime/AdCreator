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
    private final Collection<String> callToActions;
    private final Collection<String> products;
    private final Collection<String> sizes;
    private final Collection<String> weights;
    private final Collection<String> templateIds;
    private final Collection<String> availableTemplates;




    public String selectedDescriptionFont;
    public String selectedDescriptionFontWeight;
    public String selectedDescriptionFontSize;

    public String selectedHeaderFont;
    public String selectedHeaderFontWeight;
    public String selectedHeaderFontSize;

    public String selectedPriceFont;
    public String selectedPriceFontWeight;
    public String selectedPriceFontSize;


    public String selectedLogo;
    public String selectedCallToAction;
    public String selectedProduct;
    public String selectedTemplateId;

    public String selectedAvailableTemplate;

    public String headerFontColor;
    public String priceFontColor;
    public String descriptionFontColor;



    public String descriptionText;
    public String headerText;
    public String priceText;

    public String backgroundColor;


    //{"product":{"height":45,"width":45,"origin_x":2,"origin_y":2},"logo":{"height":50,"width":50,"origin_x":270,"origin_y":0},"title":{"height":40,"width":210,"origin_x":55,"origin_y":7},"price":{"height":0,"width":0,"origin_x":0,"origin_y":0},"description":{"height":20,"width":250,"origin_x":10,"origin_y":28},"name":"template_8","calltoaction":{"height":0,"width":0,"origin_x":0,"origin_y":0},"main":{"height":50,"width":320,"origin_x":0,"origin_y":0}}

    public int productHeight, productWidth, productOriginX, productOriginY;
    public int titleHeight, titleWidth, titleOriginX, titleOriginY;
    public int descriptionHeight, descriptionWidth, descriptionOriginX, descriptionOriginY;
    public int priceHeight, priceWidth, priceOriginX, priceOriginY;
    public int logoHeight, logoWidth, logoOriginX, logoOriginY;

    public int calltoactionHeight, calltoactionWidth, calltoactionOriginX, calltoactionOriginY;
    public int mainHeight, mainWidth, mainOriginX, mainOriginY;
    public String templateDefName;








    public AdCreatorView(AdCreator adCreator, Collection<String> fonts, Collection<String> logos, Collection<String> callToActions, Collection<String> products,
                         Collection<String> sizes, Collection<String> weights, Collection<String> templateIds, Collection<String> availableTemplates
    ) {
        super(template);
        this.adCreator = adCreator;
        this.fonts = fonts;
        this.logos = logos;
        this.products = products;
        this.callToActions = callToActions;
        this.sizes = sizes;
        this.weights = weights;
        this.templateIds = templateIds;
        this.availableTemplates = availableTemplates;
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

    public List<String> getCallToActions() {
        return new ArrayList(callToActions);
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

    public Collection<String> getTemplateIds() { return templateIds; }
    public Collection<String> getAvailableTemplates() { return availableTemplates; }



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



    public String selectedAvailableTemplate(String val) {
        if (selectedAvailableTemplate == null) return null;
        else if (selectedAvailableTemplate.equals(val))
            return "selected";
        return null;
    }

    public String selectedCallToAction(String val) {
        if (selectedCallToAction == null) return null;
        else if (selectedCallToAction.equals(val))
            return "selected";
        return null;
    }

    public String selectedPriceFont(String val) {
        if (selectedPriceFont == null) return null;
        else if (selectedPriceFont.equals(val))
            return "selected";
        return null;
    }

    public String selectedPriceFontSize(String val) {
        if (selectedPriceFontSize == null) return null;
        else if (selectedPriceFontSize.equals(val))
            return "selected";
        return null;
    }

    public String selectedPriceFontWeight(String val) {
        if (selectedPriceFontWeight == null) return null;
        else if (selectedPriceFontWeight.equals(val))
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


    public String selectedTemplateId(String val) {
        if (selectedTemplateId == null) return null;
        else if (selectedTemplateId.equals(val))
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

    public String getPriceFontColor() {
        return priceFontColor;
    }


    public String getPriceText() {
        return priceText;
    }


    public String getDescriptionText() {
        return descriptionText;
    }

    public String getBackgroundColor() { return backgroundColor; }

    public String getTemplateDefName() {
        return templateDefName;
    }

    public int getProductHeight() {
        return productHeight;
    }

    public int getProductWidth() {
        return productWidth;
    }

    public int getProductOriginX() {
        return productOriginX;
    }

    public int getProductOriginY() {
        return productOriginY;
    }

    public int getTitleHeight() {
        return titleHeight;
    }

    public int getTitleWidth() {
        return titleWidth;
    }

    public int getTitleOriginX() {
        return titleOriginX;
    }

    public int getTitleOriginY() {
        return titleOriginY;
    }

    public int getDescriptionHeight() {
        return descriptionHeight;
    }

    public int getDescriptionWidth() {
        return descriptionWidth;
    }

    public int getDescriptionOriginX() {
        return descriptionOriginX;
    }

    public int getDescriptionOriginY() {
        return descriptionOriginY;
    }

    public int getPriceHeight() {
        return priceHeight;
    }

    public int getPriceWidth() {
        return priceWidth;
    }

    public int getPriceOriginX() {
        return priceOriginX;
    }

    public int getPriceOriginY() {
        return priceOriginY;
    }

    public int getCalltoactionHeight() {
        return calltoactionHeight;
    }

    public int getCalltoactionWidth() {
        return calltoactionWidth;
    }

    public int getCalltoactionOriginX() {
        return calltoactionOriginX;
    }

    public int getCalltoactionOriginY() {
        return calltoactionOriginY;
    }

    public int getMainHeight() {
        return mainHeight;
    }

    public int getMainWidth() {
        return mainWidth;
    }

    public int getMainOriginX() {
        return mainOriginX;
    }

    public int getMainOriginY() {
        return mainOriginY;
    }

    public int getLogoHeight() {
        return logoHeight;
    }

    public int getLogoWidth() {
        return logoWidth;
    }

    public int getLogoOriginX() {
        return logoOriginX;
    }

    public int getLogoOriginY() {
        return logoOriginY;
    }
}
