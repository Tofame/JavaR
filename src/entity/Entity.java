package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.io.IOException;

import conditions.Condition;
import main.GamePanel;
import main.UtilityTool;
import main.UI;

public class Entity {
    GamePanel gp;
    UtilityTool uTool = new UtilityTool();

    public int worldX, worldY;

    public String name = "Entity";
    public String direction = "down";
    public int speed = 1;
    // CREATURE STATISTICS
    String healthBarColor = "#00b800";
    public int maxHealth = 100;
    public int health = 100;
    public float healthPercent = (float)health/maxHealth;

    String manaBarColor = "#4361EE";
    public int maxMana;
    public int mana;
    public float manaPercent;

    public boolean hideHealth = false;
    public boolean hideMana = false;
    public boolean hideName = false;

    public Condition[] conditions = new Condition[10]; // Player has more
    public int amountOfConditions = 0;

    public int noMoveConditions = 0;

    public enum CreatureType {
        MONSTER,
        PLAYER,
        NPC,
        OBJECT
    };
    protected CreatureType creatureType;

    public BufferedImage spriteSheet, upIdle, up1, up2, downIdle, down1, down2, leftIdle, left1, left2, rightIdle, right1, right2;
    public int spriteOffsetX, spriteOffsetY = 0;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int singleFrameWidth = 32; // size of character's sprite single frame
    public int singleFrameHeight = 32; // size of character's sprite single frame

    public Rectangle solidArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;

    public String dialogues[] = new String[20];
    public int dialogueIndex;

    // SuperObject
    public BufferedImage image;
    public boolean hasCollision = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }


    // Methods of Entity
    public void changeHealth(int value) {
        health = health + value;
        if(health > maxHealth)
            health = maxHealth;
        
        if(health < 0) {
            // handle death here
            healthPercent = 0;
        } else {
            healthPercent = (float)health/maxHealth;
        }
    }

    public void changeHealthByPercent(int value, boolean basedOnActualHealth) {
        int healthToAdd = 0;
        if(basedOnActualHealth)
            healthToAdd = (int)(value/100 * health);
        else
            healthToAdd = (int)(value/100 * maxHealth);
    
        health = health + healthToAdd;
        if(health > maxHealth)
            health = maxHealth;
        
        if(health < 0) {
            // handle death here
            healthPercent = 0;
        } else {
            healthPercent = (float)health/maxHealth;
        }
    }

    public void changeMana(int value) {
        mana = mana + value;
        if(mana > maxMana)
            mana = maxMana;
        
        if(mana < 0) {
            // handle death here
            manaPercent = 0;
        } else {
            manaPercent = (float)mana/maxMana;
        }
    }

    public void changeManaByPercent(int value, boolean basedOnActualMana) {
         int manaToAdd = 0;
        if(basedOnActualMana)
            manaToAdd = (int)(value/100 * mana);
        else
            manaToAdd = (int)(value/100 * maxMana);
    
        mana = mana + manaToAdd;
        if(mana > maxMana)
            mana = maxMana;
        
        if(mana < 0) {
            // handle death here
            manaPercent = 0;
        } else {
            manaPercent = (float)mana/maxMana;
        }
    }

    public void increaseMaxHealth(int value) {
        maxHealth += value;
        if(maxHealth < health) // in case the max health was decreased
            health = maxHealth;
        healthPercent = (float)health/maxHealth;
    }

    public void increaseMaxMana(int value) {
        maxMana += value;
        if(maxMana < mana) // in case the max mana was decreased
            mana = maxMana;
        manaPercent = (float)mana/maxMana;
    }

    public void changeSpeed(int value) {
        speed += value;
    }

    public boolean isAbleToMove() {
        if(noMoveConditions > 0) {
            return false;
        }

        return true;
    }

    public void setAction() {}

    public void speak() {}

    public boolean isAbleToAttack() { return true; }

    public void doAttack(Entity entity) {}

    // BELOW ARE METHODS THAT ARE USED LESS OFTEN THROUGHOUT THE PROJECT (like update() is used once)

    public void update() { // Player has his own update method. This one is for monsters and entities
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkPlayer(this);

        // IF CONDITION IS FALSE THEN CAN MOVE
        if(collisionOn == false && isAbleToMove() == true) {
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

    public void updateConditions() {
        for(int i = 0; i < amountOfConditions; i++) {
            gp.conditionHandler.conditionCheckup(this, i);
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
            drawNameAndBars(g2, name, screenX + singleFrameWidth/2 + spriteOffsetX, screenY - singleFrameHeight, 1);

            if(GamePanel.drawCollisions) {
                g2.setColor(Color.RED);
                g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, (int) solidArea.getWidth(), (int) solidArea.getHeight());
            }
        }
    }

    public void setDefaultImages(String fileName, String folderType, boolean spriteSheetExists /* it means that there is an image that we can use e.g. from character creator */) {
        loadSpriteSheet(fileName, folderType, spriteSheetExists);
        setFrameImages();
    }

    public void loadSpriteSheet(String fileName, String folderType, boolean spriteSheetExists /* it means that there is an image that we can use e.g. from character creator */) {
        if(!spriteSheetExists) {
            try {
                spriteSheet = uTool.loadImage("res/" + folderType + "/" + fileName);
                int tempSingleFrameWidth = spriteSheet.getWidth() / 4;
                int tempSingleFrameHeight = spriteSheet.getHeight() / 3;
                singleFrameWidth = tempSingleFrameWidth * gp.scale;
                singleFrameHeight = tempSingleFrameHeight * gp.scale;
                spriteOffsetX = -singleFrameWidth/2;
                spriteOffsetY = -singleFrameHeight + 18;
                spriteSheet = uTool.scaleImage(spriteSheet, singleFrameWidth * 4, singleFrameHeight * 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            int tempSingleFrameWidth = spriteSheet.getWidth() / 4;
            int tempSingleFrameHeight = spriteSheet.getHeight() / 3;
            singleFrameWidth = tempSingleFrameWidth * gp.scale;
            singleFrameHeight = tempSingleFrameHeight * gp.scale;
            spriteOffsetX = -singleFrameWidth/2;
            spriteOffsetY = -singleFrameHeight + 18;
            spriteSheet = uTool.scaleImage(spriteSheet, singleFrameWidth * 4, singleFrameHeight * 3);
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
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void drawNameAndBars(Graphics2D g2, String text, int x, int y, int borderSize) {
        // Draw Health here
        if(!hideHealth) {
            if(healthPercent == 1) { // light green hp
                healthBarColor = "#00b800";
            } else if(healthPercent > 0.6) { // green hp
                healthBarColor = "#4e9e4e";
            } else if(healthPercent > 0.3) { // yellow hp
                healthBarColor = "#9e9e00";
            } else if(healthPercent > 0.01) { // red hp
                healthBarColor = "#8e0f0f";
            } else { // dark red hp
                healthBarColor = "#2b0f0f";
            }
            // Outline
            g2.setColor(Color.BLACK);
            g2.fillRect(x - gp.healthBarWidth/2 - 1, y + 8 - 1, gp.healthBarWidth + 2, gp.healthBarHeight + 2); // border/2,border/2,bordersize,bordersize

            g2.setColor(UI.hexToColor(healthBarColor, 255));
            g2.fillRect(x - gp.healthBarWidth/2, y + 8, (int)(healthPercent * gp.healthBarWidth), gp.healthBarHeight);
        }
    
        if(!hideMana) {
            // Outline
            g2.setColor(Color.BLACK);
            g2.fillRect(x - gp.healthBarWidth/2 - 1, y + 12 - 1, gp.healthBarWidth + 2, gp.healthBarHeight + 2); // border/2,border/2,bordersize,bordersize

            g2.setColor(UI.hexToColor(manaBarColor, 255));
            g2.fillRect(x - gp.healthBarWidth/2, y + 12, (int)(manaPercent * gp.healthBarWidth), gp.healthBarHeight);
        }

        if(!hideName) {
            g2.setFont(UI.verdana_bold_15);
            x = x - (int)(g2.getFontMetrics().getStringBounds(text, g2).getWidth()/2);

            String nameColorHex;
            switch(creatureType) {
                case PLAYER:
                    nameColorHex = "#5ac752";
                    break;
                case MONSTER:
                    nameColorHex = "#6b001d";
                    break;
                case NPC:
                    nameColorHex = "#286eb8";
                    break;
                default:
                    nameColorHex = "#5ac752";
                    break;
            }

            g2.setColor(UI.hexToColor("#1a2c06", 255));
            g2.drawString(text, x + borderSize, y - borderSize);
            g2.drawString(text, x + borderSize, y + borderSize);
            g2.drawString(text, x - borderSize, y - borderSize);
            g2.drawString(text, x - borderSize, y + borderSize);

            g2.setColor(UI.hexToColor(nameColorHex, 255));
            g2.drawString(text, x, y);
        }
    }
}