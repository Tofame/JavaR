package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Key;

public class UI {
    GamePanel gp;
    Font arial_40;
    BufferedImage keyImage;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void draw(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        //g2.drawString("FPS: " + gp.shownFPS, 25, 50); // if you want to draw FPS then also uncomment in GamePanel in the game loop
        g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x " + gp.player.hasKey, 88, 80);

        // Draw player name
        g2.drawString("Tofame", gp.player.screenX - 40, gp.player.screenY - 10);
    }
}
