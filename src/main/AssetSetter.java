package main;

import entity.NPC;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setupObjects() {

    }

    public void setupNPCs() {
        configureNPC("hugeBandit.png", 0, "Old man", 21, 21, 46, 30, 0, 0);
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

}
