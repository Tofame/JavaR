package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import conditions.Condition;
import effects.Position;
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

    // Player methods
    public void doAttack() {
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        if(monsterIndex != -1) {
            doCombat(this, gp.monster[monsterIndex]);
        }

        sendWeaponEffect();
    }

    public void sendWeaponEffect() {
        Position position = gp.player.getPosition();

        switch(this.direction) {
            case 1: // up
                position.y -= gp.tileSize / 2;
                gp.magicEffectHandler.sendMagicEffect(position, 1, 1);
                break;
            case 2: // right
                position.x += gp.tileSize / 2;
                gp.magicEffectHandler.sendMagicEffect(position, 2, 1);
                break;  
            case 3: // down
                position.y += gp.tileSize / 2;
                gp.magicEffectHandler.sendMagicEffect(position, 3, 1);  
                break;
            case 4: // left
                position.x -= gp.tileSize / 2;
                gp.magicEffectHandler.sendMagicEffect(position, 4, 1);   
                break;
            default:
                break;
        }
    }

    /* Test functions temporarily here to test knife paperdoll */
    public void updatePaperdolls() {
        try {
            this.armorPaperdoll = uTool.loadImage("res/paperDolls/Armor/armor01.png");
            this.armorPaperdoll = uTool.scaleImage(this.armorPaperdoll, singleFrameWidth * 4, singleFrameHeight * 3);

            this.leftHandPaperdoll = uTool.loadImage("res/paperDolls/LH/knife01.png");
            this.leftHandPaperdoll = uTool.scaleImage(this.leftHandPaperdoll, singleFrameWidth * 4, singleFrameHeight * 3);

            this.rightHandPaperdoll = uTool.loadImage("res/paperDolls/RH/knife01.png");
            this.rightHandPaperdoll = uTool.scaleImage(this.rightHandPaperdoll, singleFrameWidth * 4, singleFrameHeight * 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setSpriteSheet(uTool.combineImages(spriteSheet, armorPaperdoll), false);
        this.setSpriteSheet(mergeHandPaperdoll(leftHandPaperdoll, true), false);
        this.setSpriteSheet(mergeHandPaperdoll(rightHandPaperdoll, false), true);
    }
    public void updatePaperdolls2() {
        this.setSpriteSheet(characterSpriteSheet, true);
    }
    // End of test functions

    public void setSpriteSheet(BufferedImage newSpritesheet, boolean setFrameImages) {
        this.spriteSheet = newSpritesheet;
        if(setFrameImages)
            this.setFrameImages();
    }

    // the 1st column of paperdoll's frames (going up/north) ... have to be drawn below spritesheet and the other frames above it
    // This is for hand paperdolls - e.g. so sword while going north wouldn't appear on your hair
    public BufferedImage mergeHandPaperdoll(BufferedImage paperdoll, boolean leftHand /* false = rightHand */) {
        BufferedImage newSpritesheet = new BufferedImage(spriteSheet.getWidth(), spriteSheet.getHeight(), BufferedImage.TYPE_INT_ARGB);

        int width = paperdoll.getWidth();

        Graphics2D g2 = newSpritesheet.createGraphics();

        if(leftHand) {
            // 1st and 2nd row BELOW player (going north, going east)
            g2.drawImage(paperdoll.getSubimage(0, 0, width * 2/4, paperdoll.getHeight()), 0, 0, null);
            // BODY -----
            g2.drawImage(this.spriteSheet, 0, 0, null);
            // 3rd, 4th row draw ON body
            g2.drawImage(paperdoll.getSubimage(width * 2/4, 0, width * 2/4, paperdoll.getHeight()), width * 2/4, 0, null);
        } else {
            // 1st row BELOW player (going north)
            g2.drawImage(paperdoll.getSubimage(0, 0, width/4, paperdoll.getHeight()), 0, 0, null);
            // 4th row BELOW player (going west)
            g2.drawImage(paperdoll.getSubimage(width * 3/4, 0, width/4, paperdoll.getHeight()), width * 3/4, 0, null);
            // BODY -----
            g2.drawImage(this.spriteSheet, 0, 0, null);
            // 2nd, 3rd row draw ON body
            g2.drawImage(paperdoll.getSubimage(width/4, 0, width/2, paperdoll.getHeight()), width/4, 0, null);
        }

        g2.dispose();
        return newSpritesheet;
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

        attackArea.width = 36;
        attackArea.height = 36;

        // Starting position, name etc.
        setDefaultValues();

        // Dont change, its like a camera position
        screenX = gp.screenWidth/2;
        screenY = gp.screenHeight/2;
    }
    public void setDefaultValues() {
        worldX = gp.tileSize * 23; //2240; // Starting X position, gp.tileSize * 23
        worldY = gp.tileSize *21; //2400; // Starting Y position
        setName("Tofame");
        speed = 4;
        direction = 3;

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
                direction = 1;
            } 
            if(keyH.downPressed == true) {
                direction = 3;
            }
            if(keyH.leftPressed == true) {
                direction = 4;
            }
            if(keyH.rightPressed == true) {
                direction = 2;
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
                    case 1:
                        worldY -= speed;
                        break;
                    case 3:
                        worldY += speed;
                        break;
                    case 4:
                        worldX -= speed;
                        break;
                    case 2:
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
        if(i != -1) {

        }
    }

    public void interactNPC(int i) {
        if(i != -1) {
            if(gp.keyH.enterPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void contactMonster(int i) {
        if(i != -1) {
            gp.monster[i].doAttack(this);
        }
    }

    public boolean isInSight(int worldX, int worldY) {
        return worldX + this.singleFrameWidth > this.worldX - this.screenX &&
               worldX - this.singleFrameWidth < this.worldX + this.screenX &&
               worldY + this.singleFrameHeight > this.worldY - this.screenY &&
               worldY - this.singleFrameHeight < this.worldY + this.screenY;
    }

    @Override
    public void draw(Graphics2D g2) { // Draws player
        BufferedImage image = null;

        switch(direction) {
            case 1:
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
            case 3:
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
            case 4:
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
            case 2:
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

        // DEBUG
		// AttackArea
		int tempScreenX = screenX + solidArea.x;
		int tempScreenY = screenY + solidArea.y;		
		switch(direction) {
		case 1: tempScreenY = screenY - attackArea.height; break;
		case 3: tempScreenY = screenY + attackArea.height; break; 
		case 4: tempScreenX = screenX - attackArea.width; break;
		case 2: tempScreenX = screenX + attackArea.width; break;
		}				
		g2.setColor(Color.red);
		g2.drawRect(tempScreenX, tempScreenY, attackArea.width, attackArea.height);
    }
}