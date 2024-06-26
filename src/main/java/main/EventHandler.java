package main;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[GamePanel.maxWorldCol][GamePanel.maxWorldRow];

        int col = 0;
        int row = 0;
        while(col < GamePanel.maxWorldCol && row < GamePanel.maxWorldRow) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].x = gp.tileSize/2 - eventRect[col][row].width/2;
            eventRect[col][row].y = gp.tileSize/2 - eventRect[col][row].height/2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == GamePanel.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent() {
        // Check if player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if(canTouchEvent) {
            if(hit(27,16,2) == true) {teleport(27, 16, gp.dialogueState);}
            if(hit(23,16,0) == true) {damagePit(23, 16, gp.dialogueState);}
            if(hit(23,12,1) == true) {healingPool(23, 12, gp.dialogueState);}
        }
    }
    public boolean hit(int col, int row, int reqDirection) {
        boolean hit = false;
        if(eventRect[col][row].eventDone == true)
            return hit;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        if(gp.player.solidArea.intersects(eventRect[col][row])) {
            if(reqDirection == 0 || gp.player.direction == reqDirection) {
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
        // Reset values
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
        return hit;
    }

    public void teleport(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Teleport!";
        gp.player.worldX = gp.tileSize*37;
        gp.player.worldY = gp.tileSize*10;
    }

    public void damagePit(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall into a pit!";
        gp.player.changeHealth(-2);
        gp.conditionHandler.addCondition(gp.player, gp.conditionHandler.basicFire, 9);
        canTouchEvent = false;
        gp.player.updatePaperdolls();
    }

    public void healingPool(int col, int row, int gameState) {
        if(gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You drank Wisla's muddy water.\nYour life has been recovered.";
            gp.player.changeHealth(20);
            eventRect[col][row].eventDone = true; // will make it 1 time event
            gp.conditionHandler.addCondition(gp.player, gp.conditionHandler.basicIncreaseMaxHealth, 9);
            gp.player.updatePaperdolls2();
        }
    }
}