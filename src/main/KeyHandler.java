package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    // DEBUG
    public boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            // WHICH TITLE SCREEN
            if(gp.ui.titleScreenState == 0) { // NEW GAME, LOAD GAME, EXIT
                if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 2;
                    } 
                }
                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 2) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if(gp.ui.commandNum == 0) {
                        gp.ui.titleScreenState = 1;
                    } else if(gp.ui.commandNum == 1) {
                        // add later
                    } else if(gp.ui.commandNum == 2) {
                        System.exit(0);
                    }
                }
            } else if(gp.ui.titleScreenState == 1) { // CUSTOMIZE CHARACTER
                if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 3;
                    } 
                }
                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 3) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (gp.ui.commandNum <= gp.charCreator.amountOfBodyParts && (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT)) {
                    switch(gp.ui.commandNum) {
                        case 0:
                            if(gp.charCreator.chosenBodyIndex > 0) {
                                gp.charCreator.chosenBodyIndex--;
                                gp.charCreator.setupCharacterOutfit(false);
                            }
                            break;
                        case 1:
                            if(gp.charCreator.chosenHairIndex > 0) {
                                gp.charCreator.chosenHairIndex--;
                                gp.charCreator.setupCharacterOutfit(false);
                            }
                            break;
                        case 2:
                            if(gp.charCreator.chosenClothIndex > 0) {
                                gp.charCreator.chosenClothIndex--;
                                gp.charCreator.setupCharacterOutfit(false);
                            }
                            break;
                        case 3:
                            if(gp.charCreator.chosenLegsIndex > 0) {
                                gp.charCreator.chosenLegsIndex--;
                                gp.charCreator.setupCharacterOutfit(false);
                            }
                            break;
                    }
                }
                if (gp.ui.commandNum <= gp.charCreator.amountOfBodyParts && (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT)) {
                    switch(gp.ui.commandNum) {
                        case 0:
                            gp.charCreator.chosenBodyIndex++;
                            gp.charCreator.setupCharacterOutfit(false);
                            break;
                        case 1:
                            gp.charCreator.chosenHairIndex++;
                            gp.charCreator.setupCharacterOutfit(false);
                            break;
                        case 2:
                            gp.charCreator.chosenClothIndex++;
                            gp.charCreator.setupCharacterOutfit(false);
                            break;
                        case 3:
                            gp.charCreator.chosenLegsIndex++;
                            gp.charCreator.setupCharacterOutfit(false);
                            break;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                }
            } else if(gp.ui.titleScreenState == 2) { // CLASS SELECTION
                if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 3;
                    } 
                }
                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 3) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if(gp.ui.commandNum == 0) {
                        System.out.println("Warrior");
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    } else if(gp.ui.commandNum == 1) {
                        System.out.println("Rogue");
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    } else if(gp.ui.commandNum == 2) {
                        System.out.println("Sorcerer");
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    } else if(gp.ui.commandNum == 3) {
                        gp.ui.titleScreenState = 0;
                        gp.ui.commandNum = 0;
                    }
                }
            }
        // PLAY STATE
        } else if(gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }

            // DEBUG
            if (code == KeyEvent.VK_T) {
                checkDrawTime = !checkDrawTime;
            }
        }
        // PAUSE STATE
        else if(gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }
        // DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState) {
            if(code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }
    
}