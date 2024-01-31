package effects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import main.GamePanel;
import main.UI;
import main.UtilityTool;

public class MagicEffectHandler {
    GamePanel gp;
    UtilityTool uTool = new UtilityTool();
    public static String effectFolderPath = "res/effects";
    private static int maximumEffectsOnScreen = 200; // sets the initial size of ArrayList that holds the effects that will/are displayed
    public int effectsAmount = 0; // amount of .png files in /effects

    public static double singleFrameInterval = 0.07;

    public MagicEffect[] effects; // defined effects ready to use, e.g. fireball index 0

    public MagicEffectHandler(GamePanel gp) {
        this.gp = gp;

        this.countEffects();
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

// Magic Effects list
    public void countEffects() {
        URL folderUrl = MagicEffectHandler.class.getClassLoader().getResource(MagicEffectHandler.effectFolderPath);
        if (folderUrl == null) {
            System.out.println("[MagicEffectHandler] The specified directory does not exist or is not a directory.");
            return;
        }

        Path folderPath;
        try {
            folderPath = Paths.get(folderUrl.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        File folder = folderPath.toFile();
        // Get the list of files in the directory
        File[] files = folder.listFiles();

        if (files == null) {
            System.out.println("[MagicEffectHandler] Couldnt get the length of effects folder.");
            return;
        }

        this.effectsAmount = files.length;
    }

    public MagicEffect defineEffect(String id) throws IOException {
        BufferedImage effectSheet = uTool.loadImage(MagicEffectHandler.effectFolderPath + "/" + id + ".png");
        int frameSize = effectSheet.getWidth();

        return new MagicEffect(uTool.scaleImage(effectSheet, frameSize * GamePanel.scale, frameSize * GamePanel.scale * (effectSheet.getHeight()/frameSize)));
    }
}