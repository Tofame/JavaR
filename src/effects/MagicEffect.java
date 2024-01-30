package effects;
import java.awt.image.BufferedImage;

import main.Animator;

public class MagicEffect {
    public BufferedImage sheet;

    public Animator animator;
    public Position position;

    public double timeEnd;

    public MagicEffect(BufferedImage spritesheet) {
        this.sheet = spritesheet;
    }

    public void loadAnimator(double singleFrameInterval) {
        animator = new Animator(sheet, (int)(sheet.getHeight()/sheet.getWidth()), singleFrameInterval);
        animator.start();
    }
}