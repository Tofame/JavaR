package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        // Collision square for player
        solidArea = new Rectangle();
        solidArea.width = 16;
        solidArea.height = 16;
        solidArea.x = 0; // X offset of collision
        solidArea.y = 0; // Y offset of collision
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        // The above solidArea MUST be initialized before setDefaultImages (loadSpritesheet uses this for offset creation)

        setDefaultImages();
        setDefaultValues();

        // Dont change, its like a camera position
        screenX = gp.screenWidth/2 - (singleFrameWidth/2);
        screenY = gp.screenHeight/2 - (singleFrameHeight/2);
    }
    public void setDefaultValues() {
        worldX = 2240; //2240; // Starting X position, gp.tileSize * 23
        worldY = 2400; //2400; // Starting Y position
        setName("Tofame");
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
            } 
            if(keyH.downPressed == true) {
                direction = "down";
            }
            if(keyH.leftPressed == true) {
                direction = "left";
            }
            if(keyH.rightPressed == true) {
                direction = "right";
            }

            // CHECK PLAYER COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // IF CONDITION IS FALSE, PLAYER CAN MOVE
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
        } else { // No buttons clicked
            // Reset walking animation back to idle
            spriteNum = 1;
            spriteCounter = 0;
        }
    }

    public void pickUpObject(int i) {
        if(i != 999) {

        }
    }

    public void draw(Graphics2D g2) { // Draws player
        // If you want to draw the collision square
        //g2.setColor(Color.RED);
        //g2.fillRect(screenX + solidArea.x, screenY + solidArea.y, (int) solidArea.getWidth(), (int) solidArea.getHeight());

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

        g2.drawImage(image, screenX + spriteOffsetX, screenY + spriteOffsetY, null);
    }
}
