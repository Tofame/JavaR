package object;

import java.io.IOException;

import main.GamePanel;

public class OBJ_Door extends SuperObject {
    GamePanel gp;

    public OBJ_Door(GamePanel gp) {
        name = "Door";
        try {
            image = uTool.loadImage("res/objects/door.png");
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}