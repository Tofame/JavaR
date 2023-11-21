package entity;

import java.awt.image.BufferedImage;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Entity {
    public int x, y;
    public int speed;

    public BufferedImage spriteSheet, upIdle, up1, up2, downIdle, down1, down2, leftIdle, left1, left2, rightIdle, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int singleFrameWidth = 32; // size of character's sprite single frame
    public int singleFrameHeight = 32; // size of character's sprite single frame

    public void loadSpriteSheet(String fileName) {
        try {
            spriteSheet = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/characters/" + fileName));
            singleFrameWidth = spriteSheet.getWidth() / 4;
            singleFrameHeight = spriteSheet.getHeight() / 3;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage getSprite(int col, int row) {
        return spriteSheet.getSubimage(col * singleFrameWidth, row * singleFrameHeight, singleFrameWidth, singleFrameHeight);
    }

    public void setFrameImages() {
        upIdle = getSprite(0, 0);
        up1 = getSprite(0, 1);
        up2 = getSprite(0, 2);
    
        downIdle = getSprite(2, 0);
        down1 = getSprite(2, 1);
        down2 = getSprite(2, 2);
    
        leftIdle = getSprite(3, 0);
        left1 = getSprite(3, 1);
        left2 = getSprite(3, 2);
    
        rightIdle = getSprite(1, 0);
        right1 = getSprite(1, 1);
        right2 = getSprite(1, 2);
    }
}