package object;

import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

public class SuperObject extends Entity {

    public SuperObject(GamePanel gp, String name) {
        super(gp);

        this.creatureType = CreatureType.OBJECT;
        this.hideHealth = true;
        this.hideMana = true;
        this.hideName = true;

        this.solidArea = new Rectangle(0, 0, 64, 64);
        this.name = name;
    }
}