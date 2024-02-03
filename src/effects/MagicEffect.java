package effects;
import java.awt.image.BufferedImage;

import main.Animator;

public class MagicEffect {
    public BufferedImage sheet;

    public int offsetX = 0;
    public int offsetY = 0;

    public Animator animator;
    public Position position;

    public double timeEnd;

    public MagicEffect(BufferedImage spritesheet) {
        this.sheet = spritesheet;
    }

    public void loadAnimator(double singleFrameInterval) {
        animator = new Animator(sheet, singleFrameInterval);
    }
}