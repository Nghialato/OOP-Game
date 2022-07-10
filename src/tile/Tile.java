package tile;

import java.awt.image.BufferedImage;

public class Tile {
    protected BufferedImage image_center, image2, image1, image;
    public boolean collision = true; // Check collision true or false
    protected int explosion = 0; // 0: Nothing, 1: Can be explosion, 2: Can not be explosion
}