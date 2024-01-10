package entity;

import java.util.Random;

import main.GamePanel;

public class Monster extends Entity {
    public double lastAttack = 0;
    public double attackSpeed = 1.5; // 1.5 -> 1 attack each 1,5s
    public int attackValue = 1;

    public Monster(GamePanel gp, String name, String spritesheetPath) {
        super(gp);

        this.name = name;
        this.creatureType = CreatureType.MONSTER;
        this.hideMana = true;

        setDefaultImages(spritesheetPath, "monsters", false);
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
            direction = "down";

            actionLockCounter = 0;
        }
    }

    public void doAttack(Entity target) {
        if(isAbleToAttack()) {
            target.changeHealth(-this.attackValue);
            this.lastAttack = gp.ui.playTime + this.attackSpeed;
        }
    }

    public boolean isAbleToAttack() {
        if(this.lastAttack > gp.ui.playTime) {
            return false;
        }
        return true;
    }
}