package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    // FPS
    int FPS = 60;
    int currentFPS = 0;

    Font fpsFont = new Font("Arial", Font.PLAIN, 12);

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // ≈ 0.0167 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                currentFPS++;
            }

            if (timer >= 1000000000) {
                //System.out.println("FPS: " + currentFPS);
                currentFPS = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if(keyH.upPressed == true) {
            playerY -= playerSpeed;
        } 
        if(keyH.downPressed == true) {
            playerY += playerSpeed;
        }
        if(keyH.leftPressed == true) {
            playerX -= playerSpeed;
        }
        if(keyH.rightPressed == true) {
            playerX += playerSpeed;
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        // Draw player
        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);

        // Draw fps in left upper corner
        //g2.setFont(fpsFont);
        //g2.setColor(Color.white);
        //g2.drawString("FPS: " + currentFPS, 10, 20);

        g2.dispose();
    }
}