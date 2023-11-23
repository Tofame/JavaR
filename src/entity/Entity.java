package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
    GamePanel gp;

    public int worldX, worldY;
    public int speed;

    public String name = "Entity";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage spriteSheet, upIdle, up1, up2, downIdle, down1, down2, leftIdle, left1, left2, rightIdle, right1, right2;
    public int spriteOffsetX, spriteOffsetY = 0;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public int singleFrameWidth = 32; // size of character's sprite single frame
    public int singleFrameHeight = 32; // size of character's sprite single frame

    public void loadSpriteSheet(String fileName) {
        UtilityTool uTool = new UtilityTool();

        try {
            spriteSheet = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/characters/" + fileName));
            int tempSingleFrameWidth = spriteSheet.getWidth() / 4;
            int tempSingleFrameHeight = spriteSheet.getHeight() / 3;
            singleFrameWidth = tempSingleFrameWidth * gp.scale;
            singleFrameHeight = tempSingleFrameHeight * gp.scale;
            spriteOffsetX = -singleFrameWidth/2 + solidArea.width/2;
            spriteOffsetY = -singleFrameHeight + 18;
            spriteSheet = uTool.scaleImage(spriteSheet, singleFrameWidth * 4, singleFrameHeight * 3);
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