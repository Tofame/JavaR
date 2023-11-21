package entity;

import java.awt.image.BufferedImage;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Entity {
    final int originalTileSize = 32; // size of character frame (should be same as tileSize from GamePanel hence this naming)

    public int x, y;
    public int speed;

    public BufferedImage spriteSheet, upIdle, up1, up2, downIdle, down1, down2, leftIdle, left1, left2, rightIdle, right1, right2;
    public String direction;

    public void loadSpriteSheet(String fileName) {
        try {
            String temp = "/characters/" + fileName;
            spriteSheet = ImageIO.read(getClass().getResourceAsStream(temp));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage getSprite(int col, int row) {
        return spriteSheet.getSubimage(col * originalTileSize, row * originalTileSize, originalTileSize, originalTileSize);
    }

    public void setFrameImages() {
        upIdle = getSprite(0, 0);
        up1 = getSprite(0, 1);
        up2 = getSprite(0, 2);
    
        downIdle = getSprite(1, 0);
        down1 = getSprite(1, 1);
        down2 = getSprite(1, 2);
    
        leftIdle = getSprite(2, 0);
        left1 = getSprite(2, 1);
        left2 = getSprite(2, 2);
    
        rightIdle = getSprite(3, 0);
        right1 = getSprite(3, 1);
        right2 = getSprite(3, 2);
    }
}