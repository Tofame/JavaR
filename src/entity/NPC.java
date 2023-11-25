package entity;

import java.util.Random;

import main.GamePanel;

public class NPC extends Entity {

    public NPC(GamePanel gp, String name, String spritesheetPath) {
        super(gp);

        direction = "down";
        speed = 4;
        this.name = name;

        setDefaultImages(spritesheetPath);
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

}
