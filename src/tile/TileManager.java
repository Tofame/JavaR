package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

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
    }

    public void setupTile(int index, String imagePath, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = uTool.loadImage("res/tiles/" + imagePath + ".png");
            int TileWidth = tile[index].image.getWidth() * gp.scale;
            int TileHeight = tile[index].image.getHeight() * gp.scale;
            if(TileWidth > gp.tileSize) {
                tile[index].offsetX = TileWidth - gp.tileSize;
            } else if (TileWidth < gp.tileSize) {
                tile[index].offsetX = -TileWidth/2;
            }
            if(TileHeight > gp.tileSize) {
                tile[index].offsetY = TileHeight - gp.tileSize;
            } else if (TileHeight < gp.tileSize) {
                tile[index].offsetY = -TileHeight/2;
            }
            tile[index].image = uTool.scaleImage(tile[index].image, TileWidth, TileHeight);
            tile[index].collision = collision;
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

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                while(col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;

                    col++;
                }
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch(Exception e) {

        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.player.singleFrameWidth > gp.player.worldX - gp.player.screenX && 
                worldX - gp.player.singleFrameWidth < gp.player.worldX + gp.player.screenX &&
                worldY + gp.player.singleFrameHeight > gp.player.worldY - gp.player.screenY &&
                worldY - gp.player.singleFrameHeight < gp.player.worldY + gp.player.screenY)
            {
                g2.drawImage(tile[tileNum].image, screenX - tile[tileNum].offsetX, screenY - tile[tileNum].offsetY, null);
                // If you wanted to make sure what has collision and also draw it
                /*
                if(tile[tileNum].collision) {
                    g2.setColor(Color.RED);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
                    g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                }
                */
            }
            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}