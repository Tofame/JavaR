package effects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import main.GamePanel;
import main.UtilityTool;

public class MagicEffectHandler {
    UtilityTool uTool = new UtilityTool();
    public static String effectFolderPath = "res/effects";
    public static int effectsAmount = 0;
    public static int singleFrameInterval = 700;

    public MagicEffect[] effects;

    public MagicEffectHandler() {
        countEffects();

        for(int i = 1; i < effectsAmount; i++) {
            try {
                effects = new MagicEffect[] {
                    defineEffect(Integer.toString(i), 8, 32) // Fireball
                };
            } catch(IOException e) {
                System.out.println("[MagicEffectHandler] Issue loading an effect to default array: " + e.getMessage()); 
            }
        }
    }

    public ArrayList<MagicEffect> gameEffects = new ArrayList<>(400);

    public void sendMagicEffect(Position position, int effectId) {
        gameEffects.add(effects[effectId]);
    }

// Magic Effects list
    public void countEffects() {
        File folder = new File(MagicEffectHandler.effectFolderPath);
        // Get the list of files in the directory
        File[] files = folder.listFiles();

        if (files == null) {
            System.out.println("[MagicEffectHandler] Couldnt get the length of effects folder.");
            return;
        }

        MagicEffectHandler.effectsAmount = files.length;
    }

    public MagicEffect defineEffect(String id, int frames, int frameSize) throws IOException {
        return new MagicEffect(uTool.scaleImage(uTool.loadImage(MagicEffectHandler.effectFolderPath + "/" + id + ".png"), frameSize * GamePanel.scale, frameSize * GamePanel.scale), frames, frameSize);
    }
}