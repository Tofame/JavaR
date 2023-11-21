package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        setDefaultImages();
    }
    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void setDefaultImages() {
        loadSpriteSheet("playerSprite.png");
        setFrameImages();
    }

    public void update() {
        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
            if(keyH.upPressed == true) {
                direction = "up";
                y -= speed;
            } 
            if(keyH.downPressed == true) {
                direction = "down";
                y += speed;
            }
            if(keyH.leftPressed == true) {
                direction = "left";
                x -= speed;
            }
            if(keyH.rightPressed == true) {
                direction = "right";
                x += speed;
            }

            spriteCounter++;
            if(spriteCounter > 7) {
                if(spriteNum < 3)
                    spriteNum = 3;
                else
                    spriteNum = 2;
                spriteCounter = 0;
            }
        } else { // No buttons clicked
            // Reset walking animation back to idle
            spriteNum = 1;
            spriteCounter = 0;
        }
    }
    public void draw(Graphics2D g2) { // Draws player
        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch(direction) {
            case "up":
                if(spriteNum == 1)
                    image = upIdle;
                else if(spriteNum == 2)
                    image = up1;
                else if(spriteNum == 3)
                    image = up2;
                break;
            case "down":
                if(spriteNum == 1)
                    image = downIdle;
                else if(spriteNum == 2)
                    image = down1;
                else if(spriteNum == 3)
                    image = down2;
                break;
            case "left":
                if(spriteNum == 1)
                    image = leftIdle;
                else if(spriteNum == 2)
                    image = left1;
                else if(spriteNum == 3)
                    image = left2;
                break;
            case "right":
                if(spriteNum == 1)
                    image = rightIdle;
                else if(spriteNum == 2)
                    image = right1;
                else if(spriteNum == 3)
                    image = right2;
                break;
        }

        g2.drawImage(image, x, y, singleFrameWidth * gp.scale, singleFrameHeight * gp.scale, null);
    }
}
