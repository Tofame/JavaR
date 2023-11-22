package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Key;

public class UI {
    GamePanel gp;

    Font arial_40;
    Font verdana_bold_15;
    Font martel_15;

    BufferedImage keyImage;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        verdana_bold_15 = new Font("Verdana Bold", Font.PLAIN, 15);
        martel_15 = FontLoader.loadFont("martel.ttf", 15);

        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void draw(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        //g2.drawString("FPS: " + gp.shownFPS, 25, 50); // if you want to draw FPS then also uncomment in GamePanel in the game loop
        g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x " + gp.player.hasKey, 88, 80);

        // Draw Coordinates
        g2.drawString("X: " + gp.player.worldX, gp.screenWidth - 150, 50);
        g2.drawString("Y: " + gp.player.worldY, gp.screenWidth - 150, 100);

        // Draw player name
        drawBorderedText(g2, gp.player.name, gp.player.screenX + gp.player.nameOffsetX, gp.player.screenY - 10, hexToColor("#00ef00"), hexToColor("#0d2e03"), verdana_bold_15, 1);
    }

    public static Color hexToColor(String hex) {
        // Remove '#' if present
        hex = hex.replace("#", "");

        // Convert hex to RGB
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);

        // Create and return Color object
        return new Color(r, g, b);
    }

    private void drawBorderedText(Graphics2D g2, String text, int x, int y, Color textColor, Color borderColor, Font font, int borderSize) {
        g2.setFont(font);
        g2.setColor(borderColor);
        g2.drawString(text, x + borderSize, y - borderSize);
        g2.drawString(text, x + borderSize, y + borderSize);
        g2.drawString(text, x - borderSize, y - borderSize);
        g2.drawString(text, x - borderSize, y + borderSize);

        g2.setColor(textColor);
        g2.drawString(text, x, y);
    }
}
