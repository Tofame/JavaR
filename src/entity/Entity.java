package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
    GamePanel gp;

    public int worldX, worldY;

    public String name = "Entity";
    public String direction;
    public int speed;

    public BufferedImage spriteSheet, upIdle, up1, up2, downIdle, down1, down2, leftIdle, left1, left2, rightIdle, right1, right2;
    public int spriteOffsetX, spriteOffsetY = 0;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int singleFrameWidth = 32; // size of character's sprite single frame
    public int singleFrameHeight = 32; // size of character's sprite single frame

    public Rectangle solidArea = new Rectangle(0, 0, 64, 64);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }


    // Methods of Entity
    public void setAction() {

    }
    public void update() {
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);

        // IF CONDITION IS FALSE THEN CAN MOVE
        if(collisionOn == false) {
            switch(direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;  
                
            }
        }

        spriteCounter++;
        if(spriteCounter > 7) {
            if(spriteNum < 3)
                spriteNum = 3;
            else
                spriteNum = 2;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.player.singleFrameWidth > gp.player.worldX - gp.player.screenX && 
            worldX - gp.player.singleFrameWidth < gp.player.worldX + gp.player.screenX &&
            worldY + gp.player.singleFrameHeight > gp.player.worldY - gp.player.screenY &&
            worldY - gp.player.singleFrameHeight < gp.player.worldY + gp.player.screenY)
        {
            // If you want to draw the collision square NPC
            g2.setColor(Color.RED);
            g2.fillRect(screenX + solidArea.x, screenY + solidArea.y, (int) solidArea.getWidth(), (int) solidArea.getHeight());

            BufferedImage image = null;

            switch(direction) {
                case "up":
                    if(collisionOn == false) {
                        // Here animation of walking
                        if(spriteNum == 1)
                            image = upIdle;
                        else if(spriteNum == 2)
                            image = up1;
                        else if(spriteNum == 3)
                            image = up2;
                    } else {
                        image = upIdle;
                    }
                    break;
                case "down":
                    if(collisionOn == false) {
                        // Here animation of walking
                        if(spriteNum == 1)
                            image = downIdle;
                        else if(spriteNum == 2)
                            image = down1;
                        else if(spriteNum == 3)
                            image = down2;
                    } else {
                        image = downIdle;
                    }
                    break;
                case "left":
                    if(collisionOn == false) {
                        // Here animation of walking
                        if(spriteNum == 1)
                            image = leftIdle;
                        else if(spriteNum == 2)
                            image = left1;
                        else if(spriteNum == 3)
                            image = left2;
                    } else {
                        image = leftIdle;
                    }
                    break;
                case "right":
                    if(collisionOn == false) {
                        // Here animation of walking
                        if(spriteNum == 1)
                            image = rightIdle;
                        else if(spriteNum == 2)
                            image = right1;
                        else if(spriteNum == 3)
                            image = right2;
                    } else {
                        image = rightIdle;
                    }
                    break;
            }

            g2.drawImage(image, screenX, screenY, null);
        }
    }

    public void setDefaultImages(String fileName) {
        loadSpriteSheet(fileName);
        setFrameImages();
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}