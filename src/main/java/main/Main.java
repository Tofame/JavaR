package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws Exception {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("JavaR");

        GamePanel gp = new GamePanel();
        window.add(gp);

        gp.setupGame();
        gp.startGameThread();

        DrawLogic drawLogic = new DrawLogic(gp);
        drawLogic.startDrawThread();
        window.add(drawLogic);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}