package main;

import java.io.IOException;

import entity.Entity;
import entity.NPC;
import object.SuperObject;

public class AssetSetter {
    GamePanel gp;
    UtilityTool uTool = new UtilityTool();

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setupObjects() {
        loadObject(0, 26, 18, "door", "Door", true, true);

        loadObject(1, 24, 21, "hugeGatePortal", "Portal", true, false);

        loadObject(2, 24, 18, "door", "Door", true, false);
        setObjectOffset(2, true, false, 16, 16);
    }

    public void setupNPCs() {
        configureNPC("hugeBandit.png", 0, "Old man", 21, 21, 46, 30, 0, 0);
        String[] tempDialogues = { "Hi, they call me a bike stealer, but call me an Old man.", "Im a tibia pro.", "Cwiras, cwiras, cwiras." };
        configureNPCDialogues(0, tempDialogues);
    }

    // Methods for simplyfying the processs
    public void configureNPC(String spritesheetPath, int index, String name, int x, int y, int collisionWidth, int collisionHeight, int collisionOffsetX, int collisionOffsetY) {
        gp.npc[index] = new NPC(gp, name, spritesheetPath);
        gp.npc[index].worldX = gp.tileSize * x;
        gp.npc[index].worldY = gp.tileSize * y;
        if(collisionWidth == 0) {
            collisionWidth = 16;
        }
        gp.npc[index].solidArea.width = collisionWidth;
        gp.npc[index].spriteOffsetX = gp.npc[index].spriteOffsetX + collisionWidth/2;
        if(collisionHeight == 0) {
            collisionHeight = 16;
        }
        gp.npc[index].solidArea.height = collisionHeight;
        //gp.npc[index].spriteOffsetY = gp.npc[index].spriteOffsetY + collisionHeight/2;
        if(collisionOffsetX != 0) {
            gp.npc[index].solidArea.x = collisionOffsetX;
            gp.npc[index].solidAreaDefaultX = collisionOffsetX;
        }
        if(collisionOffsetY != 0) {
            gp.npc[index].solidArea.y = collisionOffsetY;
            gp.npc[index].solidAreaDefaultY = collisionOffsetY;
        }
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
        gp.obj[index].collision = hasCollision;

        try {
            gp.obj[index].downIdle = uTool.loadImage("res/objects/" + spriteName + ".png");
            int downIdleWidth = gp.obj[index].downIdle.getWidth() * gp.scale;
            int downIdleHeight = gp.obj[index].downIdle.getHeight() * gp.scale;
            if(forceTileSize) {
                gp.obj[index].downIdle = uTool.scaleImage(gp.obj[index].downIdle, gp.tileSize, gp.tileSize);
            } else {
                gp.obj[index].downIdle = uTool.scaleImage(gp.obj[index].downIdle, downIdleWidth, downIdleHeight);
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
}