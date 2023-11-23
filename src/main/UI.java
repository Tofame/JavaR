package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
    GamePanel gp;

    Font arial_40, arial_80B, verdana_bold_15, martel_30;

    BufferedImage keyImage;

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

        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        if(gameFinished == true) {
            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "You found the treasure!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (int)(gp.player.singleFrameHeight * 3);
            g2.drawString(text, x, y);

            text = "Your time is: " + dFormat.format(playTime) + "!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (int)(gp.player.singleFrameHeight * 8);
            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);
            text = "Congratulations!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (int)(gp.player.singleFrameHeight * 5);
            g2.drawString(text, x, y);

            gp.gameThread = null;
        } else {
            g2.setFont(arial_40);
            g2.setColor(Color.white);

            //Draw Play Time
            if(showPlayTime == true) {
                playTime += (double)1/60;
                g2.drawString("Play time: " + dFormat.format(playTime), gp.screenWidth - 270, 200);
            }
            // Draw FPS
            if(showFPS == true)
                g2.drawString("FPS: " + gp.shownFPS, 25, 50);
            // Draw Coordinates
            if(showCoordinates == true) {
                g2.drawString("X: " + gp.player.worldX, gp.screenWidth - 150, 50);
                g2.drawString("Y: " + gp.player.worldY, gp.screenWidth - 150, 100);
            }
            // Draw Key count
            g2.drawImage(keyImage, 20, 75, null);
            g2.drawString("x " + gp.player.hasKey, 88, 120);
            // Draw player name
            drawName(g2, gp.player.name, gp.player.screenX + 7, gp.player.screenY - gp.player.singleFrameHeight - 5, 1, true);
            // Draw message
            if(messageOn == true) {
                g2.setFont(martel_30);
                g2.drawString(message, gp.tileSize/2, gp.tileSize*5);

                messageCounter++;

                if(messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
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
