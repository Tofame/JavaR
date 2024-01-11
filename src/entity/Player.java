package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import conditions.Condition;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    // Spritesheet layers below
    // From Entity.java: 
    //     BufferedImage spriteSheet
    public BufferedImage characterSpriteSheet; // we save here character created: without any paperdolls etc., just straight out of the character creator
    public BufferedImage armorPaperdoll, necklacePaperdoll, leftHandPaperdoll, rightHandPaperdoll, backPaperdoll = null;

    /* Test functions temporarily here to test knife paperdoll */
    public void updatePaperdolls() {
        try {
            this.leftHandPaperdoll = uTool.loadImage("res/paperDolls/knife01LH.png");
            this.leftHandPaperdoll = uTool.scaleImage(this.leftHandPaperdoll, singleFrameWidth * 4, singleFrameHeight * 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setSpriteSheet(uTool.combineImages(spriteSheet, leftHandPaperdoll));
    }
    public void updatePaperdolls2() {
        this.setSpriteSheet(characterSpriteSheet);
    }

    public void setSpriteSheet(BufferedImage newSpritesheet) {
        this.spriteSheet = newSpritesheet;
        this.setFrameImages();
    }

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        this.creatureType = CreatureType.PLAYER;
        // Collision square for player
        solidArea = new Rectangle();
        solidArea.width = 26;
        solidArea.height = 32;
        solidArea.x = -solidArea.width/2; // X offset of collision
        solidArea.y = -18; // Y offset of collision
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        // The above solidArea MUST be initialized before setDefaultImages (loadSpritesheet uses this for offset creation)

        setDefaultImages("playerSprite.png", "characters", false);
        setDefaultValues();
        this.characterSpriteSheet = this.spriteSheet;

        // Dont change, its like a camera position
        screenX = gp.screenWidth/2;
        screenY = gp.screenHeight/2;
    }
    public void setDefaultValues() {
        worldX = gp.tileSize * 23; //2240; // Starting X position, gp.tileSize * 23
        worldY = gp.tileSize *21; //2400; // Starting Y position
        setName("Tofame");
        speed = 4;
        direction = "down";

        // PLAYER STATS
        maxHealth = 150;
        health = 150;
        healthPercent = (float)health/maxHealth;

        maxMana = 150;
        mana = 150;
        manaPercent = (float)mana/maxMana;

        conditions = new Condition[20];
    }

    public void update() {
        if(((keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) && isAbleToMove() == true) || keyH.enterPressed == true) {
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
            interactObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // CHECK EVENT COLLISION
            gp.eHandler.checkEvent();

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false && keyH.enterPressed == false) {
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

            gp.keyH.enterPressed = false;

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

    public void interactObject(int i) {
        if(i != 999) {

        }
    }

    public void interactNPC(int i) {
        if(i != 999) {
            if(gp.keyH.enterPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void contactMonster(int i) {
        if(i != 999) {
            gp.monster[i].doAttack(this);
        }
    }

    @Override
    public void draw(Graphics2D g2) { // Draws player
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
        drawNameAndBars(g2, gp.player.name, gp.player.screenX, gp.player.screenY - gp.player.singleFrameHeight, 1);

        if(GamePanel.drawCollisions) {
            g2.setColor(Color.ORANGE);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, (int) solidArea.getWidth(), (int) solidArea.getHeight());
        }
    }
}