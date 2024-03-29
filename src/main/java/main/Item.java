package main;

import java.awt.image.BufferedImage;

import main.GamePanel.slots;

public class Item {
    protected int id;

    public Item() {
        this.id = GamePanel.itemsCount++;
    }

    protected BufferedImage image;
}

class Tile extends Item {
    public int offsetX;
    public int offsetY;

    public boolean collision = false;

    public int type = 0; // 0 - common, 1 - ground border, 2 - bottom, 3 - top
    public boolean isGround = false;
    public int speed = 0;
}

class EquipmentItem extends Item {
    public slots slot = slots.SLOT_NONE;
    public int stackable = -1; // -1 not stackable, other number means max stack
}