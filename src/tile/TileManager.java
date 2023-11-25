package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import java.awt.AlphaComposite;
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

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("res/maps/world01.txt");
    }

    public void getTileImage() {
        setupTile(0, "grass", false);
        setupTile(1, "wall", true);
        setupTile(2, "water", true);
        setupTile(3, "earth", false);
        setupTile(4, "tree", true);
        setupTile(5, "sand", false);
        setupTile(6, "64", true);
        setupTile(7, "hugeGatePortal", true);
    }

    public void setupTile(int index, String imagePath, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/tiles/" + imagePath + ".png"));
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