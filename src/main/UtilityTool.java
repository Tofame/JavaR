package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }

    public BufferedImage combineImages(BufferedImage BiggerImage, BufferedImage SmallerImage) {
        // Determine the dimensions of the combined image
        int combinedWidth = Math.max(BiggerImage.getWidth(), SmallerImage.getWidth());
        int combinedHeight = Math.max(BiggerImage.getHeight(), SmallerImage.getHeight());

        BufferedImage combinedImage = new BufferedImage(combinedWidth, combinedHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = combinedImage.createGraphics();

        g2.drawImage(BiggerImage, 0, 0, null);
        g2.drawImage(SmallerImage, 0, 0, null);

        g2.dispose();
        return combinedImage;
    }
}