package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JPanel;

import effects.MagicEffect;
import entity.Entity;

public class DrawLogic extends JPanel implements Runnable {
    Thread drawThread;
    GamePanel gp;

    // SETTINGS
        // FPS
    static final int maxDrawFPS = 60; // the higher it is, the more frequent the drawing is
        // Calculating average draw time
    static final int resetDrawsAt = 10000;
    static int amountOfDraws = 0;
    static double sumDrawsTotal = 0;

    public DrawLogic(GamePanel gp) {
        this.gp = gp;
        this.setPreferredSize(new Dimension(gp.screenWidth, gp.screenHeight));
        this.setBackground(Color.black);
    }

    public void startDrawThread() {
        drawThread = new Thread(this);
        drawThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / maxDrawFPS; // ≈ 0.0167 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (drawThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                repaint();
                delta--;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        // DEBUG
        long drawStart = 0;
        if(gp.keyH.checkDrawTime == true)
        {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if(gp.gameState == gp.titleState) {
            gp.ui.draw(g2);
        }
        // OTHERS
        else {
          // This includes drawing order
            // TILE
            gp.tileM.draw(g2);

            // EFFECTS BELOW ENTITIES
            Iterator<MagicEffect> iterator = gp.magicEffectHandler.gameEffectsBelow.iterator();
            while (iterator.hasNext()) {
                MagicEffect effect = iterator.next();
                
                if (effect.timeEnd < UI.playTime) {
                    iterator.remove();
                    continue;
                }

                if(gp.player.isInSight(effect.position.x, effect.position.y)) {
                    g2.drawImage(effect.animator.getCurrentFrame(), effect.position.x - gp.player.worldX + gp.player.screenX, effect.position.y - gp.player.worldY + gp.player.screenY, null);
                }
            }
            
            // ADD ENTITIES TO THE LIST
            gp.entityList.add(gp.player);
            for(int i = 0; i < gp.npc.length; i++) {
                if(gp.npc[i] != null) {
                    gp.entityList.add(gp.npc[i]);
                }
            }
            for(int i = 0; i < gp.obj.length; i++) {
                if(gp.obj[i] != null) {
                    gp.entityList.add(gp.obj[i]);
                }
            }
            for(int i = 0; i < gp.monster.length; i++) {
                if(gp.monster[i] != null) {
                    gp.entityList.add(gp.monster[i]);
                }
            }
            // SORT
            Collections.sort(gp.entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY + e1.solidAreaDefaultY, e2.worldY + e2.solidAreaDefaultY);
                    return result;
                }
            });

            // DRAW ENTITIES
            for(int i = 0; i < gp.entityList.size(); i++) {
                gp.entityList.get(i).draw(g2);
            }

            // EMPTY ENTITY LIST
            gp.entityList.clear();

            //UI
            gp.ui.draw(g2);
        }

        //DEBUG END
        if(gp.keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw time: " + passed, 10, 500);
            // Average draw time implementation
            g2.drawString("Average draw time: " + (sumDrawsTotal/amountOfDraws), 10, 600);
            if(amountOfDraws >= resetDrawsAt) {
                amountOfDraws = 0;
                sumDrawsTotal = 0;
            } else {
                amountOfDraws++;
                sumDrawsTotal = (double)sumDrawsTotal + passed;
            }
        }

        g2.dispose();
    }    
}
