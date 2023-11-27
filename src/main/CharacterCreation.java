package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CharacterCreation {
    GamePanel gp;
    UtilityTool uTool = new UtilityTool();

    // Chracter Images variables
    public BufferedImage finalSpriteSheet; // spritesheet created with character creation
    BufferedImage bodyImage;
    BufferedImage hairImage;
    BufferedImage clothImage;
    int chosenBodyIndex = 0;
    int chosenHairIndex = 0;
    int chosenClothIndex = 0;

    public CharacterCreation(GamePanel gp) {
        this.gp = gp;

        try {
            this.bodyImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/characterCreator/body00.png"));
            this.hairImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/characterCreator/hair00.png"));
            this.clothImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/characterCreator/cloth00.png"));
            this.finalSpriteSheet = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/characterCreator/body00.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage setupCharacterOutfit() {

        return this.finalSpriteSheet;
    }
}