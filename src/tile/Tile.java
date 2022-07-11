package tile;

import main.GamePanel;

import java.awt.image.BufferedImage;

public class Tile {
    protected int worldX, worldY;
    GamePanel gp;
    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }
    protected BufferedImage image_center, image2, image1, image;
    public boolean collision = true; // Check collision true or false
    protected int explosion = 0; // 0: Nothing, 1: Can be explosion, 2: Can not be explosion
}