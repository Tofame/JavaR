package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[100];
        mapTileNum = new int[GamePanel.maxWorldCol][GamePanel.maxWorldRow];

        getTileImage();
        loadMap("res/maps/worldV2.txt");
    }

    public void getTileImage() {
        // PLACEHOLDER
        setupTile(0, "grass00", false);
        setupTile(1, "grass00", false);
        setupTile(2, "grass00", false);
        setupTile(3, "grass00", false);
        setupTile(4, "grass00", false);
        setupTile(5, "grass00", false);
        setupTile(6, "grass00", false);
        setupTile(7, "grass00", false);
        setupTile(8, "grass00", false);
        setupTile(9, "grass00", false);
        // PLACEHOLDER

        setupTile(10, "grass00", false);
        setupTile(11, "grass01", false);
        setupTile(12, "water00", true);
        setupTile(13, "water01", true);
        setupTile(14, "water02", true);
        setupTile(15, "water03", true);
        setupTile(16, "water04", true);
        setupTile(17, "water05", true);
        setupTile(18, "water06", true);
        setupTile(19, "water07", true);
        setupTile(20, "water08", true);
        setupTile(21, "water09", true);
        setupTile(22, "water10", true);
        setupTile(23, "water11", true);
        setupTile(24, "water12", true);
        setupTile(25, "water13", true);
		setupTile(26, "road00", false);
        setupTile(27, "road01", false);
        setupTile(28, "road02", false);
        setupTile(29, "road03", false);
        setupTile(30, "road04", false);
        setupTile(31, "road05", false);
        setupTile(32, "road06", false);
        setupTile(33, "road07", false);
        setupTile(34, "road08", false);
        setupTile(35, "road09", false);
        setupTile(36, "road10", false);
        setupTile(37, "road11", false);
        setupTile(38, "road12", false);
        setupTile(39, "earth", false);
        setupTile(40, "wall", true);
        setupTile(41, "tree", true);
        setupTile(42, "tree", true); // test
    }

    public void setupTile(int index, String imagePath, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = uTool.loadImage("res/tiles/" + imagePath + ".png");
            int TileWidth = tile[index].image.getWidth() * GamePanel.scale;
            int TileHeight = tile[index].image.getHeight() * GamePanel.scale;

            int combineTileOffsetX = 0;
            int combineTileOffsetY = 0;
            if(TileWidth > gp.tileSize) {
                tile[index].offsetX = TileWidth - gp.tileSize;
                combineTileOffsetX = TileWidth - gp.tileSize;
            } else if (TileWidth < gp.tileSize) {
                //tile[index].offsetX = -TileWidth/2;
            }
            if(TileHeight > gp.tileSize) {
                tile[index].offsetY = TileHeight - gp.tileSize;
                combineTileOffsetY = TileHeight - gp.tileSize;
            } else if (TileHeight < gp.tileSize) {
                //tile[index].offsetY = -TileHeight/2;
            }
            tile[index].image = uTool.scaleImage(tile[index].image, TileWidth, TileHeight);
            tile[index].collision = collision;

            // Test
            if(index == 42 || index == 40) {
                tile[index].image = uTool.combineTileImages(tile[10].image, tile[index].image, combineTileOffsetX, combineTileOffsetY);
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < GamePanel.maxWorldCol && row < GamePanel.maxWorldRow) {
                String line = br.readLine();
                while(col < GamePanel.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;

                    col++;
                }
                if(col == GamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch(Exception e) {

        }
    }

    public void draw(Graphics2D g2) {
        // TO-DO: What if we started at the left corner of the player screen instead of while-loop from 0,0
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < GamePanel.maxWorldCol && worldRow < GamePanel.maxWorldRow) {
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            if(gp.player.isInSight(worldX, worldY))
            {
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                int tileNum = mapTileNum[worldCol][worldRow];

                g2.drawImage(tile[tileNum].image, screenX - tile[tileNum].offsetX, screenY - tile[tileNum].offsetY, null);
                if(GamePanel.drawTileCollisions) {
                    if(tile[tileNum].collision) {
                        g2.setColor(Color.RED);
                        g2.drawRect(screenX - tile[tileNum].offsetX - 1, screenY - tile[tileNum].offsetY - 1, gp.tileSize, gp.tileSize);
                    }
                }
            }
            worldCol++;

            if(worldCol == GamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}