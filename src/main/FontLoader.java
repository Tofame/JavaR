package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;


public class FontLoader {
    public static Font loadFont(String fontFileName, int fontSize) {
        try {
            InputStream fontFileStream = FontLoader.class.getClassLoader().getResourceAsStream("res/fonts/" + fontFileName);
            Font tempFont = Font.createFont(Font.TRUETYPE_FONT, fontFileStream);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(tempFont);
            return tempFont.deriveFont(Font.PLAIN, fontSize);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            // Return a default font if loading fails
            System.out.println("Loading font failed. (" + fontFileName + ")");
            return new Font("Arial", Font.PLAIN, fontSize);
        }
    }
}