package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws Exception {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("TMS_Java");

        GamePanel gp = new GamePanel();
        window.add(gp);

        //window.pack();

        //window.setLocationRelativeTo(null);
        //window.setVisible(true);

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