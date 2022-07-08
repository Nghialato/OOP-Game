package entity;

import main.GamePanel;
import tile.Bomb;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    protected int worldX, worldY, max_health, current_health;
    public int getWorldY() {
        return worldY;
    }
    public int getWorldX() {
        return worldX;
    }
    protected int speed;
    public int getSpeed() {
        return speed;
    }
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    protected String direction;
    public String getDirection() {
        return direction;
    }
    protected int spriteCounter = 0;
    protected int spriteNum = 1;
    protected Rectangle solidArea;
    public Rectangle getSolidArea(){
        return solidArea;
    }
    public boolean collisionOn = false;

    protected boolean check_in_range(Bomb bomb, GamePanel gp) {
        int col1 = worldX + 8;
        int row1 = worldY + 16;
        int bombx = bomb.getWorldX();
        int bomby = bomb.getWorldY();

        return (Math.abs(col1 - bombx) <= gp.tileSize && row1 >= bomby - (bomb.bomb_range_top) * gp.tileSize - 24 && row1 <= bomby + (bomb.bomb_range_bot + 1) * gp.tileSize)
                || (Math.abs(row1 - bomby) <= gp.tileSize && col1 >= bombx - (bomb.bomb_range_left) * gp.tileSize - 24 && col1 <= bombx + (bomb.bomb_range_right + 1) * gp.tileSize);
    }
}
