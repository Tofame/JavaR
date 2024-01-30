package effects;
import java.awt.image.BufferedImage;

public class MagicEffect {
    public BufferedImage sheet;

    public int frameSize = 32;
    public int frames;

    public double timeEnd;
    public int currentFrame = 0;
    public Position position;

    public MagicEffect(BufferedImage spritesheet, int frames, int frameSize) {
        this.sheet = spritesheet;

        this.frameSize = frameSize;
        this.frames = frames;
    }
}