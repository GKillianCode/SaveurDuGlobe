package com.killiangodet.recette.utils;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

public class ImageUtils {

    @Value("${image_output_format}")
    private static String imageOutputFormat;

    public static void createImageFile(String imageBase64, String filePath, String fileName, String imageOutputFormat) throws Exception {
        String extension = getImageExtension(imageBase64);
        if (!isValidImageExtension(extension)) {
            throw new IllegalArgumentException("Image format not valid");
        }
        if (StringUtils.isEmpty(imageBase64)) {
            throw new IllegalArgumentException("Image is required");
        }

        String[] imageData = imageBase64.split(",");
        String base64 = imageData[1];

        BufferedImage bufferedImage = createBufferedImage(base64, extension);

        if (bufferedImage != null) {
            OutputStream outputStream = new FileOutputStream(filePath+fileName+"."+imageOutputFormat);
            ImageIO.write(bufferedImage, imageOutputFormat, outputStream);
            outputStream.close();
        } else {
            throw new IOException("Base64 invalid");
        }
    }

    private static BufferedImage createBufferedImage(String base64, String extension) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
        BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);

        if(extension.equals("png")){
            BufferedImage newBufferedImage = new BufferedImage(
                    bufferedImage.getWidth(),
                    bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_BGR);
            newBufferedImage.createGraphics()
                    .drawImage(bufferedImage, 0, 0, Color.white, null);
            return newBufferedImage;
        }

        return bufferedImage;
    }

    private static String getImageExtension(String base64Image) {
        String extension = "";
        String[] parts = base64Image.split(",");
        if (parts.length > 0) {
            String imageDataPart = parts[0];
            if (imageDataPart.startsWith("data:image/")) {
                extension = imageDataPart.substring("data:image/".length(), imageDataPart.indexOf(";"));
            }
        }
        return extension;
    }

    private static boolean isValidImageExtension(String extension) {
        return extension.equalsIgnoreCase("jpeg") ||
                extension.equalsIgnoreCase("jpg") ||
                extension.equalsIgnoreCase("png") ||
                extension.equalsIgnoreCase("webp");
    }

    private static void deleteImageFile(String filePath, String fileName, String format){

    }

}
