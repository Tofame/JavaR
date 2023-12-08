package main;

import entity.NPC;
import object.SuperObject;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setupObjects() {
        loadObject(0, 26, 21, "door", "Door", true);
        loadObject(0, 26, 21, "hugeGatePortal", "Portal", true);
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

    public void loadObject(int index, int x, int y, String spriteName, String name, boolean hasCollision) {
        gp.obj[0] = new SuperObject(gp, spriteName, name);
        gp.obj[0].worldX = gp.tileSize * x;
        gp.obj[0].worldY = gp.tileSize * y;
        gp.obj[0].collision = hasCollision;
    }
}