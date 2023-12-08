package object;

import java.awt.Rectangle;
import java.io.IOException;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

public class SuperObject extends Entity {
    UtilityTool uTool = new UtilityTool();

    public SuperObject(GamePanel gp, String spriteName, String name) {
        super(gp);

        this.hideHealth = true;
        this.solidArea = new Rectangle(0, 0, 64, 64);
        this.name = name;
        try {
            this.downIdle = uTool.loadImage("res/objects/" + spriteName + ".png");
            this.downIdle = uTool.scaleImage(downIdle, gp.tileSize, gp.tileSize);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}