package main;

import java.awt.Graphics2D;
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
    BufferedImage legsImage;
    int chosenBodyIndex = 0;
    int chosenHairIndex = 0;
    int chosenClothIndex = 0;
    int chosenLegsIndex = 0;
    int currentlyLoadedBodyIndex = -1;
    int currentlyLoadedHairIndex = -1;
    int currentlyLoadedClothIndex = -1;
    int currentlyLoadedLegsIndex = -1;

    public CharacterCreation(GamePanel gp) {
        this.gp = gp;

        try {
            this.bodyImage = uTool.loadImage("res/characterCreator/body00.png");
            this.hairImage = uTool.loadImage("res/characterCreator/hair00.png");
            this.clothImage = uTool.loadImage("res/characterCreator/cloth00.png");
            this.legsImage = uTool.loadImage("res/characterCreator/legs00.png");
            this.finalSpriteSheet = uTool.loadImage("res/characterCreator/body00.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage setupCharacterOutfit() {

        return this.finalSpriteSheet;
    }

    public void drawCurrentOutfitElements(Graphics2D g2) {
        try {
            if(chosenHairIndex > 9) {
                this.hairImage = uTool.loadImage("res/characterCreator/hair" + chosenHairIndex + ".png");
            } else {
                this.hairImage = uTool.loadImage("res/characterCreator/hair0" + chosenHairIndex + ".png");
            }
            g2.drawImage(hairImage, gp.ui.getXforCenteredImage(bodyImage), gp.tileSize*4, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}