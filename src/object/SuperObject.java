package object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.GamePanel;
import main.UtilityTool;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 64, 64); // 2 * 32 (tileSize) = 64
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    UtilityTool uTool = new UtilityTool();

    public SuperObject(GamePanel gp, String spriteName, String name) {
        this.name = name;
        try {
            this.image = uTool.loadImage("res/objects/" + spriteName + ".png");
            this.image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.player.singleFrameWidth > gp.player.worldX - gp.player.screenX && 
            worldX - gp.player.singleFrameWidth < gp.player.worldX + gp.player.screenX &&
            worldY + gp.player.singleFrameHeight > gp.player.worldY - gp.player.screenY &&
            worldY - gp.player.singleFrameHeight < gp.player.worldY + gp.player.screenY)
        {
            g2.drawImage(image, screenX, screenY, null);
            // If you want to draw the collision square on Object
            g2.setColor(Color.ORANGE);
            g2.drawRect(screenX - solidArea.x/2, screenY - solidArea.y/2, (int) solidArea.getWidth(), (int) solidArea.getHeight());
        }
    }
}