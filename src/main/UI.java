package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;

    public static Font arial_40, arial_80B, verdana_bold_15, martel_30;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public boolean gameFinished = false;
    public String currentDialogue = "";

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
        } else if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        } else if(gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
    }

    public void drawPlayScreen() {
        // Draw center of the screen
        // g2.fillRect(gp.screenWidth/2 - 10, gp.screenHeight/2 - 10, 20, 20);
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
    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        // WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 26));
        x += gp.tileSize;
        y += gp.tileSize;
        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        g2.setColor(hexToColor("#000000", 140));
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(hexToColor("#ffffff", 255));
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    public static Color hexToColor(String hex, int transparency) {
        // Remove '#' if present
        hex = hex.replace("#", "");

        // Convert hex to RGB
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);

        // Create and return Color object
        return new Color(r, g, b, transparency);
    }

    public void drawBorderedText(Graphics2D g2, String text, int x, int y, Color textColor, Color borderColor, Font font, int borderSize) {
        // drawBorderedText(g2, gp.player.name, gp.player.screenX, gp.player.screenY - 10, hexToColor("#00ef00", 255), hexToColor("#0d2e03", 255), verdana_bold_15, 1);
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