package effects;

import java.awt.image.BufferedImage;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.io.IOException;

import main.GamePanel;
import main.UI;
import main.UtilityTool;

public class MagicEffectHandler {
    GamePanel gp;
    public static String effectFolderPath = "effectsSheets";
    private static int maximumEffectsOnScreen = 200; // sets the initial size of ArrayList that holds the effects that will/are displayed
    public int effectsAmount = 0; // amount of .png files in /effects

    public static double singleFrameInterval = 0.07;

    public MagicEffect[] effects; // defined effects ready to use, e.g. fireball index 0

    public MagicEffectHandler(GamePanel gp) {
        this.gp = gp;

        try {
            this.countEffects();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        effects = new MagicEffect[effectsAmount + 1];

        for(int i = 0; i < this.effectsAmount; i++) {
            try {
                effects[i] = defineEffect(Integer.toString(i));
                effects[i].loadAnimator(singleFrameInterval);
            } catch(IOException e) {
                System.out.println("[MagicEffectHandler] Issue loading an effect to default array: " + e.getMessage());
            }
        }
    }

    public ArrayList<MagicEffect> gameEffectsBelow = new ArrayList<>(maximumEffectsOnScreen);
    public ArrayList<MagicEffect> gameEffectsAbove = new ArrayList<>(maximumEffectsOnScreen);

    public void sendMagicEffect(Position position, int effectId, int layer) {
        MagicEffect effect = effects[effectId];
        effect.timeEnd = UI.playTime + ((effect.sheet.getHeight()/effect.sheet.getWidth()) * MagicEffectHandler.singleFrameInterval);

        effect.position = position;
        effect.animator.start();

        if(layer == 0) {
            gameEffectsBelow.add(effect);
        } else {
            gameEffectsAbove.add(effect);
        }
    }

    public void sendMagicEffect(int x, int y, int effectId, int layer) {
        sendMagicEffect(new Position(x, y), effectId, layer);
    }

    public void countEffects() throws URISyntaxException, IOException {
        this.effectsAmount = UtilityTool.countFilesInAFolder(MagicEffectHandler.effectFolderPath);
    }

    public MagicEffect defineEffect(String id) throws IOException {
        BufferedImage effectSheet = UtilityTool.loadImage(MagicEffectHandler.effectFolderPath + "/" + id + ".png");
        int frameSize = effectSheet.getWidth();

        return new MagicEffect(UtilityTool.scaleImage(effectSheet, frameSize * GamePanel.scale, frameSize * GamePanel.scale * (effectSheet.getHeight()/frameSize)));
    }

    public void setOffsets() {
        // Example:
        //setEffectOffset(0, 32, 32);
    }

    public void setEffectOffset(int id, int x, int y) {
        if(effects[id] != null) {
            effects[id].offsetX = x;
            effects[id].offsetY = y;
        }
    }
}