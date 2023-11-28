package entity;

import java.util.Random;

import main.GamePanel;

public class NPC extends Entity {

    public NPC(GamePanel gp, String name, String spritesheetPath) {
        super(gp);

        this.name = name;
        this.creatureType = CreatureType.NPC;

        setDefaultImages(spritesheetPath, false);
    }

    public void setAction() {
        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // 1-100

            if(i <= 25) {
                direction = "up";
            } else if(i > 25 && i <= 50) {
                direction = "down";
            } else if(i > 50 && i <= 75) {
                direction = "left";
            } else { //(i > 75 && i <= 100)
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }

    public void speak() {
        if(dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
}