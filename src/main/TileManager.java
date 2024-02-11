package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.Color;
import java.awt.Graphics2D;

public class TileManager {
    GamePanel gp;
    public Tile[] tiles;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tiles = new Tile[100];
        mapTileNum = new int[GamePanel.maxWorldCol][GamePanel.maxWorldRow];

        setupTiles();
        loadMap("res/maps/worldV3.txt");
    }

    public void setupTiles() {
        // Empty, "air" tile
        tiles[0] = new Tile();

        setupTile(false);
        setupTile(false);
        setupTile(false);
    }

    public void setupTile(boolean collision) {
        try {
            Tile tile = new Tile();
            tile.image = UtilityTool.loadImage("res/tiles/" + tile.id + ".png");

            int TileWidth = tile.image.getWidth() * GamePanel.scale;
            int TileHeight = tile.image.getHeight() * GamePanel.scale;

            if(TileWidth > gp.tileSize) {
                tile.offsetX = TileWidth - gp.tileSize;
            } else if (TileWidth < gp.tileSize) {
                //tile[index].offsetX = -TileWidth/2;
            }
            if(TileHeight > gp.tileSize) {
                tile.offsetY = TileHeight - gp.tileSize;
            } else if (TileHeight < gp.tileSize) {
                //tile[index].offsetY = -TileHeight/2;
            }
            tile.image = UtilityTool.scaleImage(tile.image, TileWidth, TileHeight);
            tile.collision = collision;

            tiles[tile.id] = tile;
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

                if(tiles[tileNum].image != null)
                    g2.drawImage(tiles[tileNum].image, screenX - tiles[tileNum].offsetX, screenY - tiles[tileNum].offsetY, null);

                if(GamePanel.drawTileCollisions) {
                    if(tiles[tileNum].collision || GamePanel.drawNonSolidTileCollision) {
                        g2.setColor(Color.RED);
                        g2.drawRect(screenX - tiles[tileNum].offsetX - 1, screenY - tiles[tileNum].offsetY - 1, gp.tileSize, gp.tileSize);
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