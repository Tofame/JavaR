package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CharacterCreation {
    GamePanel gp;
    UtilityTool uTool = new UtilityTool();

    // Chracter Images variables
    BufferedImage playerSpritesheetSingleFrame;

    BufferedImage bodyImage;
    BufferedImage hairImage;
    BufferedImage clothImage;
    BufferedImage legsImage;
    int chosenBodyIndex = 0;
    int chosenHairIndex = 0;
    int chosenClothIndex = 0;
    int chosenLegsIndex = 0;
    int currentlyLoadedBodyIndex = 0;
    int currentlyLoadedHairIndex = 0;
    int currentlyLoadedClothIndex = 0;
    int currentlyLoadedLegsIndex = 0;

    BufferedImage singleFrameBody, singleFrameHair, singleFrameCloth, singleFrameLegs;

    public CharacterCreation(GamePanel gp) {
        this.gp = gp;

        try {
            this.bodyImage = uTool.loadImage("res/characterCreator/body00.png");
            this.hairImage = uTool.loadImage("res/characterCreator/hair00.png");
            this.clothImage = uTool.loadImage("res/characterCreator/cloth00.png");
            this.legsImage = uTool.loadImage("res/characterCreator/legs00.png");

            gp.player.spriteSheet = this.bodyImage;
            playerSpritesheetSingleFrame = uTool.getIdleFrameOfSpritesheet("down", this.bodyImage, 5);

            setupCharacterOutfit(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupCharacterOutfit(boolean initialization /* first time */) {
        BufferedImage combinedImage = bodyImage;

        if(chosenBodyIndex != currentlyLoadedBodyIndex || initialization) {
            try {
                if(chosenHairIndex > 9) {
                    this.bodyImage = uTool.loadImage("res/characterCreator/body" + chosenBodyIndex + ".png");
                } else {
                    this.bodyImage = uTool.loadImage("res/characterCreator/body0" + chosenBodyIndex + ".png");
                }
                combinedImage = bodyImage;
                singleFrameBody = uTool.getIdleFrameOfSpritesheet("down", this.bodyImage, 3);
                currentlyLoadedBodyIndex = chosenHairIndex;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(chosenHairIndex != currentlyLoadedHairIndex || initialization) {
            try {
                if(chosenHairIndex > 9) {
                    this.hairImage = uTool.loadImage("res/characterCreator/hair" + chosenHairIndex + ".png");
                } else {
                    this.hairImage = uTool.loadImage("res/characterCreator/hair0" + chosenHairIndex + ".png");
                }
                combinedImage = uTool.combineImages(combinedImage, hairImage);
                singleFrameHair = uTool.getIdleFrameOfSpritesheet("down", this.hairImage, 3);
                currentlyLoadedHairIndex = chosenHairIndex;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(chosenClothIndex != currentlyLoadedClothIndex || initialization) {
            try {
                if(chosenClothIndex > 9) {
                    this.clothImage = uTool.loadImage("res/characterCreator/cloth" + chosenClothIndex + ".png");
                } else {
                    this.clothImage = uTool.loadImage("res/characterCreator/cloth0" + chosenClothIndex + ".png");
                }
                combinedImage = uTool.combineImages(combinedImage, clothImage);
                singleFrameCloth = uTool.getIdleFrameOfSpritesheet("down", this.clothImage, 3);
                currentlyLoadedClothIndex = chosenClothIndex;
            } catch (IOException e) {
                e.printStackTrace();
            }

            gp.player.spriteSheet = combinedImage;
            playerSpritesheetSingleFrame = uTool.getIdleFrameOfSpritesheet("down", combinedImage, 5);
        }
        if(chosenLegsIndex != currentlyLoadedLegsIndex || initialization) {
            try {
                if(chosenClothIndex > 9) {
                    this.legsImage = uTool.loadImage("res/characterCreator/legs" + chosenLegsIndex + ".png");
                } else {
                    this.legsImage = uTool.loadImage("res/characterCreator/legs0" + chosenLegsIndex + ".png");
                }
                combinedImage = uTool.combineImages(combinedImage, legsImage);
                singleFrameLegs = uTool.getIdleFrameOfSpritesheet("down", this.legsImage, 3);
                currentlyLoadedLegsIndex = chosenLegsIndex;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        

    }

    public void drawCurrentOutfitElements(Graphics2D g2) {
        g2.drawImage(singleFrameBody, gp.ui.getXforCenteredImage(singleFrameBody), gp.tileSize*4, null);
        g2.drawImage(singleFrameHair, gp.ui.getXforCenteredImage(singleFrameHair), gp.tileSize*6, null);
        g2.drawImage(singleFrameCloth, gp.ui.getXforCenteredImage(singleFrameCloth), gp.tileSize*8, null);
        g2.drawImage(singleFrameLegs, gp.ui.getXforCenteredImage(singleFrameLegs), gp.tileSize*10, null);
    }
}