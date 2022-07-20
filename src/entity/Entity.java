package entity;

import main.GamePanel;
import tile.Bomb;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Entity {
    protected int worldX, worldY;
    protected GamePanel gp;
    protected String name;
    protected int max_health;
    public int current_health;

    protected boolean minus_health = false;

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
    protected BufferedImage[] up = new BufferedImage[4];
    protected BufferedImage[] down = new BufferedImage[4];
    protected BufferedImage[] left = new BufferedImage[4];
    protected BufferedImage[] right = new BufferedImage[4];

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
            minus_health = true;
        }
    }
    public void getImage(){
        try {
            for(int i = 1; i <= 3; i++){
                up[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + name + "/" + name + "_up_" + i + ".png")));
                down[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + name + "/" + name + "_down_" + i + ".png")));
                left[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + name + "/" + name + "_left_" + i + ".png")));
                right[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + name + "/" + name + "_right_" + i + ".png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = up[1];
                }
                if (spriteNum == 2) {
                    image = up[2];
                }
                if(spriteNum == 3){
                    image = up[3];
                }
                if(spriteNum == 4){
                    image = null;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = down[1];
                }
                if (spriteNum == 2) {
                    image = down[2];
                }
                if(spriteNum == 3){
                    image = down[3];
                }
                if(spriteNum == 4){
                    image = null;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = left[1];
                }
                if (spriteNum == 2) {
                    image = left[2];
                }
                if(spriteNum == 3){
                    image = left[3];
                }
                if(spriteNum == 4){
                    image = null;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = right[1];
                }
                if (spriteNum == 2) {
                    image = right[2];
                }
                if(spriteNum == 3){
                    image = right[3];
                }
                if(spriteNum == 4){
                    image = null;
                }
            }
        }
        g2.drawImage(image, worldX, worldY, 48, 48, null);
    }
}
