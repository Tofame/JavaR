package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class FontLoader {
    public static Font loadFont(String fontFileName, int fontSize) {
        try {
            // Load the font file
            File fontFile = new File("src/fonts/" + fontFileName);
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            
            // Register the font with the graphics environment
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            // Create a font with the specified size
            return customFont.deriveFont(Font.PLAIN, fontSize);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            // Return a default font if loading fails
            System.out.println("Loading font failed. (" + fontFileName + ")");
            return new Font("Arial", Font.PLAIN, fontSize);
        }
    }
}
