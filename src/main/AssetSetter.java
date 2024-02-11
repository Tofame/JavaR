package main;

import java.io.IOException;

import entity.Entity;
import entity.Monster;
import entity.NPC;
import object.SuperObject;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setupObjects() {
        loadObject(0, 26, 18, "door", "Door", true, true);

        loadObject(1, 24, 21, "hugeGatePortal", "Portal", true, false);

        loadObject(2, 24, 18, "door", "Door", true, false);
        setObjectOffset(2, true, false, 16, 16);
        setObjectCollision(2, true, false, 0, 0, 0, 0);

        loadObject(3, 22, 18, "door", "Door", true, true);
        setObjectCollision(3, false, false, 64, 32, 0, 32);
    }

    public void setupNPCs() {
        configureNPC("hugeBandit.png", 0, "Old man", 15, 21, 46, 30, -66);
        String[] tempDialogues = { "Hi, they call me a bike stealer, but call me an Old man.", "Im a tibia pro.", "Cwiras, cwiras, cwiras." };
        configureNPCDialogues(0, tempDialogues);
        configureCollisionOffset(gp.npc[0], 1, -1, -30);
    }

    public void setupMonsters() {
        configureMonster("slime.png", 0, "Slime", 20, 21, 12, 12);
        configureCollisionOffset(gp.monster[0], 0, -1, -1);
    }
// ==========================================================
// ========================================================== 
    // Methods for simplyfying the processs

    // mode:
    // 0 <-> is centered, then collisionOffsetX, collisionOffsetY can be -1 in order to center by axis
    // 1 <-> centering mode that also moves by singleFrame, supports offsets > \/ (value other than -1 to disable)
    // Example: mode 1 but offsetX == -1 will mean that we use mode 0.
    public void configureCollisionOffset(Entity entity, int mode, int collisionOffsetX, int collisionOffsetY) {
        // Decide whether to center X
        if(mode == 1 && collisionOffsetX != -1) {
            entity.solidAreaDefaultX = entity.singleFrameWidth + entity.spriteOffsetX + collisionOffsetX;
            entity.solidArea.x = entity.solidAreaDefaultX;  
        } else if((mode == 0 || mode == 1) && collisionOffsetX == -1) {
            entity.solidAreaDefaultX = entity.singleFrameWidth/2 - entity.solidArea.width/2 + entity.spriteOffsetX;
            entity.solidArea.x = entity.solidAreaDefaultX;
        } else {
            entity.solidAreaDefaultX = collisionOffsetX + entity.spriteOffsetX;
            entity.solidArea.x = entity.solidAreaDefaultX;
        }
    
        // Decide whether to center Y        
        if(mode == 1 && collisionOffsetY != -1) {
            entity.solidAreaDefaultY = entity.singleFrameHeight + entity.spriteOffsetY + collisionOffsetY;
            entity.solidArea.y = entity.solidAreaDefaultY;
        } else if((mode == 0 || mode == 1) && collisionOffsetY == -1) {
            entity.solidAreaDefaultY = entity.singleFrameHeight/2 - entity.solidArea.height/2 + entity.spriteOffsetY;
            entity.solidArea.y = entity.solidAreaDefaultY;
        } else {
            entity.solidAreaDefaultY = collisionOffsetY + entity.spriteOffsetY;
            entity.solidArea.y = entity.solidAreaDefaultY;
        }
    }

    public void setSpriteOffset(Entity entity, boolean isCentered, int offsetX, int offsetY) {
        if(isCentered && offsetX == -1) {
            // NEEDS TESTING
            if(entity.singleFrameWidth == gp.tileSize * 2 /* 128 */) {
                entity.spriteOffsetX = entity.singleFrameWidth/4;
            } else {
                //entity.spriteOffsetX = entity.singleFrameWidth/2;
            }
        } else {
            entity.spriteOffsetX = offsetX;
        }
    
        // Decide whether to center Y
        if(isCentered && offsetY == -1) {
            //entity.spriteOffsetY = -entity.singleFrameHeight/2;
        } else {
            entity.spriteOffsetY = offsetY;
        }      
    }

    public void setMonsterSpriteOffset(Entity entity, boolean isCentered, int offsetX, int offsetY) {
        setSpriteOffset(entity, isCentered, offsetX, offsetY);
    }
    public void setNPCSpriteOffset(Entity entity, boolean isCentered, int offsetX, int offsetY) {
        setSpriteOffset(entity, isCentered, offsetX, offsetY);
    }

    public void configureMonster(String spritesheetPath, int index, String name, int x, int y, int collisionWidth, int collisionHeight, int spriteOffsetX, int spriteOffsetY) {
        gp.monster[index] = new Monster(gp, name, spritesheetPath);
        gp.monster[index].worldX = gp.tileSize * x;
        gp.monster[index].worldY = gp.tileSize * y;

        gp.monster[index].solidArea.width = collisionWidth;
        gp.monster[index].solidArea.height = collisionHeight;

        setSpriteOffset(gp.monster[index], false, spriteOffsetX, spriteOffsetY);
    }

    public void configureMonster(String spritesheetPath, int index, String name, int x, int y, int collisionWidth, int collisionHeight) {
        gp.monster[index] = new Monster(gp, name, spritesheetPath);
        gp.monster[index].worldX = gp.tileSize * x;
        gp.monster[index].worldY = gp.tileSize * y;

        gp.monster[index].solidArea.width = collisionWidth;
        gp.monster[index].solidArea.height = collisionHeight;

        setSpriteOffset(gp.monster[index], true, -1, -1);
    }

    public void configureMonster(String spritesheetPath, int index, String name, int x, int y, int collisionWidth, int collisionHeight, int spriteOffsetY) { // sprite centered by X
        gp.monster[index] = new Monster(gp, name, spritesheetPath);
        gp.monster[index].worldX = gp.tileSize * x;
        gp.monster[index].worldY = gp.tileSize * y;

        gp.monster[index].solidArea.width = collisionWidth;
        gp.monster[index].solidArea.height = collisionHeight;

        setSpriteOffset(gp.monster[index], true, -1, spriteOffsetY);
    }

    public void configureNPC(String spritesheetPath, int index, String name, int x, int y, int collisionWidth, int collisionHeight, int spriteOffsetX, int spriteOffsetY) {
        gp.npc[index] = new NPC(gp, name, spritesheetPath);
        gp.npc[index].worldX = gp.tileSize * x;
        gp.npc[index].worldY = gp.tileSize * y;

        if(collisionWidth == 0) {
            collisionWidth = 16;
        }

        if(collisionHeight == 0) {
            collisionHeight = 16;
        }

        gp.npc[index].solidArea.width = collisionWidth;
        gp.npc[index].solidArea.height = collisionHeight;

        setSpriteOffset(gp.monster[index], false, spriteOffsetX, spriteOffsetY);
    }

    public void configureNPC(String spritesheetPath, int index, String name, int x, int y, int collisionWidth, int collisionHeight) {
        gp.npc[index] = new NPC(gp, name, spritesheetPath);
        gp.npc[index].worldX = gp.tileSize * x;
        gp.npc[index].worldY = gp.tileSize * y;

        gp.npc[index].solidArea.width = collisionWidth;
        gp.npc[index].solidArea.height = collisionHeight;

        setSpriteOffset(gp.npc[index], true, -1, -1);
    }

    public void configureNPC(String spritesheetPath, int index, String name, int x, int y, int collisionWidth, int collisionHeight, int spriteOffsetY) {
        gp.npc[index] = new NPC(gp, name, spritesheetPath);
        gp.npc[index].worldX = gp.tileSize * x;
        gp.npc[index].worldY = gp.tileSize * y;

        gp.npc[index].solidArea.width = collisionWidth;
        gp.npc[index].solidArea.height = collisionHeight;

        setSpriteOffset(gp.npc[index], true, -1, spriteOffsetY);
    }


    public void configureNPCDialogues(int npcIndex, String[] dialogues) {
        for(int i = 0; i < dialogues.length; i++) {
            gp.npc[npcIndex].dialogues[i] = dialogues[i];
        }
    }

    public void loadObject(int index, int x, int y, String spriteName, String name, boolean hasCollision, boolean forceTileSize) {
        gp.obj[index] = new SuperObject(gp, name);
        gp.obj[index].worldX = gp.tileSize * x;
        gp.obj[index].worldY = gp.tileSize * y;
        gp.obj[index].hasCollision = hasCollision;

        try {
            gp.obj[index].downIdle = UtilityTool.loadImage("res/objects/" + spriteName + ".png");
            int downIdleWidth = gp.obj[index].downIdle.getWidth() * GamePanel.scale;
            int downIdleHeight = gp.obj[index].downIdle.getHeight() * GamePanel.scale;
            if(forceTileSize) {
                gp.obj[index].downIdle = UtilityTool.scaleImage(gp.obj[index].downIdle, gp.tileSize, gp.tileSize);
            } else {
                gp.obj[index].downIdle = UtilityTool.scaleImage(gp.obj[index].downIdle, downIdleWidth, downIdleHeight);
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void setObjectOffset(int index, boolean centerObject, boolean offsetInTiles, int offsetX, int offsetY) {
        Entity tempObj = gp.obj[index];
        if(centerObject) {
            tempObj.spriteOffsetX = tempObj.downIdle.getWidth()/2;
            tempObj.spriteOffsetY = tempObj.downIdle.getHeight()/2;
        } else { // if we are not centering, then we use offset
            if(offsetInTiles) {
                tempObj.spriteOffsetX = gp.tileSize * offsetX;
                tempObj.spriteOffsetY = gp.tileSize * offsetY;
            } else {
                tempObj.spriteOffsetX = offsetX;
                tempObj.spriteOffsetY = offsetY;
            }
        }
    }

    public void setObjectCollision(int index, boolean automaticCollision, boolean valuesInTiles, int collisionWidth, int collisionHeight, int collisionOffsetX, int collisionOffsetY) {
        Entity tempObj = gp.obj[index];
        if(automaticCollision) {
            tempObj.solidArea.width = tempObj.downIdle.getWidth();
            tempObj.solidArea.height = tempObj.downIdle.getHeight();
            tempObj.solidArea.x = tempObj.spriteOffsetX;
            tempObj.solidArea.y = tempObj.spriteOffsetY;
            tempObj.solidAreaDefaultX = tempObj.solidArea.x;
                tempObj.solidAreaDefaultY = tempObj.solidArea.y;
        } else { // if we are using hand-written values
            if(valuesInTiles) {
                tempObj.solidArea.width = gp.tileSize * collisionWidth;
                tempObj.solidArea.height = gp.tileSize * collisionHeight;
                tempObj.solidArea.x = gp.tileSize * collisionOffsetX;
                tempObj.solidArea.y = gp.tileSize * collisionOffsetY;
                tempObj.solidAreaDefaultX = tempObj.solidArea.x;
                tempObj.solidAreaDefaultY = tempObj.solidArea.y;
            } else {
                tempObj.solidArea.width = collisionWidth;
                tempObj.solidArea.height = collisionHeight;
                tempObj.solidArea.x = collisionOffsetX;
                tempObj.solidArea.y = collisionOffsetY;
                tempObj.solidAreaDefaultX = tempObj.solidArea.x;
                tempObj.solidAreaDefaultY = tempObj.solidArea.y;
            }
        }
    }
}