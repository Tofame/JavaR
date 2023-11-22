package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Entity {
    public int worldX, worldY;
    public int speed;

    public String name = "Entity";
    public int nameOffsetX = 0;

    public String getName() {
        return name;
    }

    public void setName(String name, boolean isModifyingOffset, boolean isPlayer) {
        if(isPlayer && name.length() > 12) { // Limit of 12 characters for player's name
            this.name = name.substring(0, 12);
        } else {
            this.name = name;
        }

        if(isModifyingOffset == true) {
            switch (name.length()) {
                case 1:
                    nameOffsetX = singleFrameWidth - 8;
                    break;
                case 2:
                    nameOffsetX = singleFrameWidth - 14;
                    break;
                case 3:
                    nameOffsetX = singleFrameWidth - 19;
                    break;
                case 4:
                    nameOffsetX = singleFrameWidth - 25;
                    break;
                case 5:
                    nameOffsetX = singleFrameWidth - 29;
                    break;
                case 6:
                    nameOffsetX = singleFrameWidth - 34;
                    break;
                default:
                    nameOffsetX = singleFrameWidth - 34 - ((name.length() - 6) * 1);
            }
        }
    }

    public BufferedImage spriteSheet, upIdle, up1, up2, downIdle, down1, down2, leftIdle, left1, left2, rightIdle, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

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