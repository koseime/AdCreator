package com.kosei.dropwizard.adcreator.core;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lanceriedel on 9/4/14.
 */
public class LoadJpgFromFile {


    public static byte[] extractBytes (String ImageName) throws IOException {
        // open image
        File imgPath = new File(ImageName);
        byte[] b = FileUtils.readFileToByteArray(imgPath);

        return b;
    }

}
