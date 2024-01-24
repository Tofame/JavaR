package effects;
import java.awt.image.BufferedImage;

public class MagicEffect {
    public BufferedImage effectSpritesheet;

    public int frameSize = 32;
    public int frames;
    // public int currentFrame = 1;
    //public int frameInterval;

    public int orderLayer = 0; // 0 - below entities, 1 - above entities

    public int duration;

    public MagicEffect(BufferedImage spritesheet, int frames, int frameSize) {
        this.effectSpritesheet = spritesheet;

        this.frameSize = frameSize;
        this.frames = frames;
        this.duration = frames * MagicEffectHandler.singleFrameInterval; // frames * default time per frame
    }
}