package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
    GamePanel gp;
    Graphics2D g2;

    Font arial_40, arial_80B, verdana_bold_15, martel_30;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public boolean gameFinished = false;

    // Settings for UI
    boolean showFPS = true;
    boolean showCoordinates = true;
    boolean showPlayTime = true;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        verdana_bold_15 = new Font("Verdana Bold", Font.PLAIN, 15);
        martel_30 = FontLoader.loadFont("martel.ttf", 30);
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState) {
            drawPlayScreen();
        }
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
    }

    public void drawPlayScreen() {
        //Draw Play Time
        if(showPlayTime == true) {
            playTime += (double)1/60;
            g2.drawString("Playtime: " + dFormat.format(playTime), 15, gp.screenHeight - 30);
        }
        // Draw FPS
        if(showFPS == true)
            g2.drawString("FPS: " + gp.shownFPS, 25, 50);
        // Draw Coordinates
        if(showCoordinates == true) {
            g2.drawString("X: " + gp.player.worldX, gp.screenWidth - 150, 50);
            g2.drawString("Y: " + gp.player.worldY, gp.screenWidth - 150, 100);
        }
        // Draw player name
        drawName(g2, gp.player.name, gp.player.screenX + 7, gp.player.screenY - gp.player.singleFrameHeight - 5, 1, true);
    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
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
        // drawBorderedText(g2, gp.player.name, gp.player.screenX, gp.player.screenY - 10, hexToColor("#00ef00"), hexToColor("#0d2e03"), verdana_bold_15, 1);
        g2.setFont(font);
        g2.setColor(borderColor);
        g2.drawString(text, x + borderSize, y - borderSize);
        g2.drawString(text, x + borderSize, y + borderSize);
        g2.drawString(text, x - borderSize, y - borderSize);
        g2.drawString(text, x - borderSize, y + borderSize);

        g2.setColor(textColor);
        g2.drawString(text, x, y);
    }

    private void drawName(Graphics2D g2, String text, int x, int y, int borderSize, boolean isPlayer) {
        g2.setFont(verdana_bold_15);

        if(isPlayer) {
            x = x - (int)(g2.getFontMetrics().getStringBounds(text, g2).getWidth()/2);
        }

        g2.setColor(hexToColor("#1a2c06"));
        g2.drawString(text, x + borderSize, y - borderSize);
        g2.drawString(text, x + borderSize, y + borderSize);
        g2.drawString(text, x - borderSize, y - borderSize);
        g2.drawString(text, x - borderSize, y + borderSize);

        g2.setColor(hexToColor("#5ac752"));
        g2.drawString(text, x, y);
    }
}
