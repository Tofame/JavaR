package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CharacterCreation {
    GamePanel gp;

    public final int amountOfBodyParts = 3; // 3: 0-body, 1-hair, 2-cloth, 3-legs
    // Count amount of each body part files, so we can see the max in e.g. keyHandler
    String[] bodyKeywords = {"body", "legs", "cloth", "hair"};
    int bodyParts = 0;
    int legsParts = 0;
    int clothParts = 0;
    int hairParts = 0;

    // Character Images variables
    BufferedImage playerSpritesheetSingleFrame;

    BufferedImage emptyBodyPart; // when cloth,hair or legs werent selected yet
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

        countBodyParts("src/res/characterCreator", bodyKeywords);

        try {
            this.bodyImage = UtilityTool.loadImage("res/characterCreator/body00.png");
            emptyBodyPart = UtilityTool.loadImage("res/characterCreator/A_empty.png");
            emptyBodyPart = UtilityTool.scaleImage(emptyBodyPart, gp.player.singleFrameWidth, gp.player.singleFrameHeight);

            this.hairImage = emptyBodyPart;
            this.clothImage = emptyBodyPart;
            this.legsImage = emptyBodyPart;

            gp.player.spriteSheet = this.bodyImage;
            playerSpritesheetSingleFrame = UtilityTool.getIdleFrameOfSpritesheet(3, this.bodyImage, 5);

            singleFrameHair = emptyBodyPart;
            singleFrameCloth = emptyBodyPart;
            singleFrameLegs = emptyBodyPart;

            setupCharacterOutfit(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupCharacterOutfit(boolean initialization /* first time */) {
        BufferedImage combinedImage = bodyImage;

        if(chosenBodyIndex != currentlyLoadedBodyIndex || initialization) {
            try {
                if(chosenBodyIndex > 9) {
                    this.bodyImage = UtilityTool.loadImage("res/characterCreator/body" + chosenBodyIndex + ".png");
                } else {
                    this.bodyImage = UtilityTool.loadImage("res/characterCreator/body0" + chosenBodyIndex + ".png");
                }
                combinedImage = bodyImage;
                singleFrameBody = UtilityTool.getIdleFrameOfSpritesheet(3, this.bodyImage, 3);
                currentlyLoadedBodyIndex = chosenBodyIndex;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(chosenHairIndex != currentlyLoadedHairIndex || initialization) {
            if(chosenHairIndex != 0) {
                try {
                    if(chosenHairIndex > 9) {
                        this.hairImage = UtilityTool.loadImage("res/characterCreator/hair" + chosenHairIndex + ".png");
                    } else {
                        this.hairImage = UtilityTool.loadImage("res/characterCreator/hair0" + chosenHairIndex + ".png");
                    }
                    combinedImage = UtilityTool.combineImages(combinedImage, hairImage);
                    singleFrameHair = UtilityTool.getIdleFrameOfSpritesheet(3, this.hairImage, 3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                singleFrameHair = emptyBodyPart;
            }
            currentlyLoadedHairIndex = chosenHairIndex;
        } else if(currentlyLoadedHairIndex != 0) {
            combinedImage = UtilityTool.combineImages(combinedImage, hairImage);
        }
        if(chosenClothIndex != currentlyLoadedClothIndex || initialization) {
            if(chosenClothIndex != 0) {
                try {
                    if(chosenClothIndex > 9) {
                        this.clothImage = UtilityTool.loadImage("res/characterCreator/cloth" + chosenClothIndex + ".png");
                    } else {
                        this.clothImage = UtilityTool.loadImage("res/characterCreator/cloth0" + chosenClothIndex + ".png");
                    }
                    combinedImage = UtilityTool.combineImages(combinedImage, clothImage);
                    singleFrameCloth = UtilityTool.getIdleFrameOfSpritesheet(3, this.clothImage, 3);
                    currentlyLoadedClothIndex = chosenClothIndex;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                combinedImage = UtilityTool.combineImages(combinedImage, clothImage);
                singleFrameCloth = UtilityTool.getIdleFrameOfSpritesheet(3, this.clothImage, 3);
            } else {
                singleFrameCloth = emptyBodyPart;
            }
            currentlyLoadedClothIndex = chosenClothIndex;
        } else if(currentlyLoadedClothIndex != 0) {
            combinedImage = UtilityTool.combineImages(combinedImage, clothImage);
        }
        if(chosenLegsIndex != currentlyLoadedLegsIndex || initialization) {
            if(chosenLegsIndex != 0) {
                try {
                    if(chosenClothIndex > 9) {
                        this.legsImage = UtilityTool.loadImage("res/characterCreator/legs" + chosenLegsIndex + ".png");
                    } else {
                        this.legsImage = UtilityTool.loadImage("res/characterCreator/legs0" + chosenLegsIndex + ".png");
                    }
                    combinedImage = UtilityTool.combineImages(combinedImage, legsImage);
                    singleFrameLegs = UtilityTool.getIdleFrameOfSpritesheet(3, this.legsImage, 3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                singleFrameLegs = emptyBodyPart;
            }
            currentlyLoadedLegsIndex = chosenLegsIndex;
        } else if(currentlyLoadedLegsIndex != 0) {
            combinedImage = UtilityTool.combineImages(combinedImage, legsImage);
        }

        playerSpritesheetSingleFrame = UtilityTool.getIdleFrameOfSpritesheet(3, combinedImage, 5);
        gp.player.spriteSheet = combinedImage;
    }

    public void drawCurrentOutfitElements(Graphics2D g2, int offsetY) {
        g2.drawImage(singleFrameBody, gp.ui.getXforCenteredImage(singleFrameBody), gp.tileSize*4 + offsetY, null);
        g2.drawImage(singleFrameHair, gp.ui.getXforCenteredImage(singleFrameHair), gp.tileSize*6 + offsetY + 25 /* offset for hair display */, null);
        g2.drawImage(singleFrameCloth, gp.ui.getXforCenteredImage(singleFrameCloth), gp.tileSize*8 + offsetY, null);
        g2.drawImage(singleFrameLegs, gp.ui.getXforCenteredImage(singleFrameLegs), gp.tileSize*10 + offsetY, null);
    }

    public void countBodyParts(String folderPath, String[] keywords) {
        File folder = new File(folderPath);
        // Get the list of files in the directory
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) { // if is a file - not a folder
                    // Check if the file name contains any of the specified keywords
                    for (String keyword : keywords) {
                        if (file.getName().startsWith(keyword)) {
                            switch (keyword) {
                                case "body":
                                    bodyParts++;
                                    break;
                                case "legs":
                                    legsParts++;
                                    break;
                                case "cloth":
                                    clothParts++;
                                    break;
                                case "hair":
                                    hairParts++;
                                    break;
                            }
                            break; // optimization
                        }
                    }
                }
            }
        }
    }
}