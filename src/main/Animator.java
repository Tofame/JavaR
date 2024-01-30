package main;
import java.awt.image.BufferedImage;

public class Animator {
    BufferedImage sheet;
    private int frameCount;
    private int frameIndex;
    private double startTime;
    private double singleFrameInterval;

    public Animator(BufferedImage sheet, int frameCount, double singleFrameInterval) {
        this.sheet = sheet;
        this.frameCount = frameCount;
        this.frameIndex = 0;
        this.sheet = sheet;
        this.singleFrameInterval = singleFrameInterval;
    }

    public void start() {
        startTime = UI.playTime;
    }

    public BufferedImage getCurrentFrame() {
        double currentTime = UI.playTime;
        double elapsedTime = currentTime - startTime;

        // Oblicz indeks bieżącej klatki
        frameIndex = (int) (elapsedTime / singleFrameInterval) % frameCount;

        int frameWidth = sheet.getWidth();
        return sheet.getSubimage(0, frameIndex * frameWidth, frameWidth, frameWidth);
    }
}