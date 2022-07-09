package entity;

import main.GamePanel;
import tile.Bomb;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    protected int worldX, worldY;
    protected int max_health, current_health;
    public int getWorldY() {
        return worldY;
    }
    public int getWorldX() {
        return worldX;
    }

    public int getCurrent_health() {
        return current_health;
    }
    protected int speed;
    public int getSpeed() {
        return speed;
    }
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    protected String direction;

    protected int Direction;
    public String getDirection() {
        return direction;
    }
    protected int spriteCounter = 0;
    protected int spriteNum = 1;
    protected Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Rectangle getSolidArea(){
        return solidArea;
    }
    public boolean collisionOn = false;


    protected void update_health(Bomb bomb, GamePanel gp) {
        int col1 = worldX ;
        int row1 = worldY ;
        int bombx = bomb.getWorldX();
        int bomby = bomb.getWorldY();

        if(((Math.abs(col1 - bombx) <= gp.tileSize - 16 && row1 >= bomby - (bomb.bomb_range_top) * gp.tileSize - 24 && row1 <= bomby + (bomb.bomb_range_bot + 1) * gp.tileSize - 8))
                || ((Math.abs(row1 - bomby) < (gp.tileSize - 12) && col1 >= bombx - (bomb.bomb_range_left) * gp.tileSize - 24 && col1 <= bombx + (bomb.bomb_range_right + 1) * gp.tileSize - 16)) && bomb.isExploding()){
            current_health--;
        }
    }
}
