package object;

import java.io.IOException;

import main.GamePanel;

public class OBJ_Key extends SuperObject {
    GamePanel gp;

    public OBJ_Key(GamePanel gp) {
        name = "Key";
        try {
            image = uTool.loadImage("res/objects/key.png");
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}