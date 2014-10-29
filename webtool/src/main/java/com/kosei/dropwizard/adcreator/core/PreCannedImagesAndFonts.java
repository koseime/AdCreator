package com.kosei.dropwizard.adcreator.core;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lanceriedel on 9/4/14.
 */
public class PreCannedImagesAndFonts {

    private final String fontDir;
    private final String logoDir;
    private final String productsDir;
    private final String callToActionDir;
    private final String templateDir;




    private final List<String> sizes;
    private final List<String> weights;
    private final List<String> templateIds;





    public PreCannedImagesAndFonts(String fontDir, String logoDir, String productsDir, String callToActionDir, String templateDir) {
        this.fontDir = fontDir;
        this.logoDir = logoDir;
        this.productsDir = productsDir;
        this.callToActionDir = callToActionDir;
        this.templateDir = templateDir;
        sizes = Arrays.asList("0","2","3","4","5","6","7","8","9","10","11","12","14","16","18","22","24","28");
        weights = Arrays.asList("50", "100", "200","400","600","800","1000");
        templateIds = Arrays.asList("__embedded","template_1","template_2","template_3","template_4","template_5","template_6","template_7","template_8");

    }

    public Map<String,String> getFonts() {
        Map<String,String> fonts = new HashMap<String, String>();
        File dir = new File(fontDir);
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".ttf");
            }
        });
        for (File f : files) {
            fonts.put(f.getName(),f.getName().replace(".ttf",""));
        }
        return fonts;
    }

    public Map<String,String> getLogos() {
        Map<String,String> fonts = new HashMap<String, String>();
        File dir = new File(logoDir);
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png");
            }
        });
        for (File f : files) {
            fonts.put(f.getName(),f.getAbsolutePath());
        }
        return fonts;
    }

    public Map<String,String> getProducts() {
        Map<String,String> fonts = new HashMap<String, String>();
        File dir = new File(productsDir);
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png");
            }
        });
        for (File f : files) {
            fonts.put(f.getName(),f.getAbsolutePath());
        }
        return fonts;
    }


    public Map<String,String> getCallToActions() {
        Map<String,String> fonts = new HashMap<String, String>();
        File dir = new File(callToActionDir);
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png");
            }
        });
        for (File f : files) {
            fonts.put(f.getName(),f.getAbsolutePath());
        }
        return fonts;
    }

    public Map<String,String> getTemplates() {
        Map<String,String> templates = new HashMap<String, String>();
        File dir = new File(templateDir);
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".json");
            }
        });
        for (File f : files) {
            templates.put(f.getName(),f.getAbsolutePath());
        }
        return templates;
    }

    public String getTemplateDir() {
        return templateDir;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public List<String> getWeights() {
        return weights;
    }

    public List<String> templateIds() { return templateIds; }


}
