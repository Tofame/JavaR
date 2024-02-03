package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

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

    public BufferedImage loadImage(String path) throws IOException {
        BufferedImage image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(path));
        return image;
    }

    public BufferedImage getIdleFrameOfSpritesheet(int direction, BufferedImage spritesheet, int Scale) {
        int width = spritesheet.getWidth()/4;
        int height = spritesheet.getHeight()/3;
        BufferedImage resultImage;
        switch (direction) {
            case 1:
                resultImage = spritesheet.getSubimage(0 * width, 0 * height, width, height);
                if(Scale > 1) {
                    resultImage = this.scaleImage(resultImage, Scale*width, Scale*height);
                }
                break;
            case 3:
                resultImage = spritesheet.getSubimage(2 * width, 0 * height, width, height);
                if(Scale > 1) {
                    resultImage = this.scaleImage(resultImage, Scale*width, Scale*height);
                }
                break;
            case 4:
                resultImage = spritesheet.getSubimage(3 * width, 0 * height, width, height);
                if(Scale > 1) {
                    resultImage = this.scaleImage(resultImage, Scale*width, Scale*height);
                }
                break;
            case 2:
                resultImage = spritesheet.getSubimage(4 * width, 0 * height, width, height);
                if(Scale > 1) {
                    resultImage = this.scaleImage(resultImage, Scale*width, Scale*height);
                }
                break;
            default:
                resultImage = spritesheet.getSubimage(0 * width, 0 * height, width, height);
                if(Scale > 1) {
                    resultImage = this.scaleImage(resultImage, Scale*width, Scale*height);
                }
                break;
        }
        return resultImage;
    }

    public BufferedImage combineTileImages(BufferedImage groundImage, BufferedImage wallImage, int combineOffsetX, int combineOffsetY) {
        // Determine the dimensions of the combined image
        int combinedWidth = Math.max(groundImage.getWidth(), wallImage.getWidth());
        int combinedHeight = Math.max(groundImage.getHeight(), wallImage.getHeight());

        BufferedImage combinedImage = new BufferedImage(combinedWidth, combinedHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = combinedImage.createGraphics();

        g2.drawImage(groundImage, combineOffsetX, combineOffsetY, null);
        g2.drawImage(wallImage, 0, 0, null);

        g2.dispose();
        return combinedImage;
    }
}