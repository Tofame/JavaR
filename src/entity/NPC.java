package entity;

import java.util.Random;

import main.GamePanel;

public class NPC extends Entity {

    public NPC(GamePanel gp, String name, String spritesheetPath) {
        super(gp);

        this.name = name;
        this.creatureType = CreatureType.NPC;
        this.hideMana = true;

        setDefaultImages(spritesheetPath, "characters", false);
    }

    public void setAction() {
        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // 1-100

            if(i <= 25) {
                direction = 1;
            } else if(i > 25 && i <= 50) {
                direction = 3;
            } else if(i > 50 && i <= 75) {
                direction = 4;
            } else { //(i > 75 && i <= 100)
                direction = 2;
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
            case 1:
                direction = 3;
                break;
            case 3:
                direction = 1;
                break;
            case 4:
                direction = 2;
                break;
            case 2:
                direction = 4;
                break;
        }
    }
}