package com.kosei.dropwizard.adcreator.core;

import java.io.File;
import java.io.FilenameFilter;
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


    public PreCannedImagesAndFonts(String fontDir, String logoDir, String productsDir) {
        this.fontDir = fontDir;
        this.logoDir = logoDir;
        this.productsDir = productsDir;
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
            fonts.put(f.getName(),f.getAbsolutePath());
        }
        return fonts;
    }

    public Map<String,String> getLogos() {
        Map<String,String> fonts = new HashMap<String, String>();
        File dir = new File(logoDir);
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg");
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
                return name.toLowerCase().endsWith(".jpg");
            }
        });
        for (File f : files) {
            fonts.put(f.getName(),f.getAbsolutePath());
        }
        return fonts;
    }


}
