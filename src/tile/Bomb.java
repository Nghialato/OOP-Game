package tile;

import entity.Player;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Bomb extends Tile {
    private int worldX;
    private int worldY;
    private int spriteBomb;
    private int bombnum = 0;
    public int bomb_range_left = 2, bomb_range_right = 2, bomb_range_bot = 2, bomb_range_top = 2;
    BufferedImage bomb = null;
    BufferedImage [] bomb_explosion_col;
    BufferedImage [] bomb_explosion_row;
    public int time = 0;
    public void setWorldX(int worldX) {
        this.worldX = (worldX/gp.tileSize) * gp.tileSize;
    }
    public void setWorldY(int worldY) {
        this.worldY = (worldY/gp.tileSize + 1) * gp.tileSize;
    }
    public int getWorldX() {
        return worldX;
    }
    public int getWorldY() {
        return worldY;
    }

    public Player player;
    public GamePanel gp;
    public Bomb(Player player, GamePanel gp) {
        this.player = player;
        this.gp = gp;
        bomb_explosion_col = new BufferedImage[3];
        bomb_explosion_row = new BufferedImage[3];
        loadimg();
    }

    public void loadimg(){
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/bomb.png")));
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/bomb_1.png")));
            image2 = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/tiles/bomb_2.png"))));
            image_center = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/bomb_exploded2.png")));
            for(int i = 0; i < 3; i++){
                bomb_explosion_row[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/explosion_row_" + String.valueOf(i) + ".png")));
            }
            for(int i = 0; i < 3; i++){
                bomb_explosion_col[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/explosion_col_" + String.valueOf(i) + ".png")));
            }
        }  catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        time++;
        spriteBomb++;
        if(time < 120){
            if (spriteBomb > 10) {
                if (bombnum == 0) {
                    bombnum = 1;
                    bomb = image1;

                } else if (bombnum == 1) {
                    bombnum = 2;
                    bomb = image2;
                } else if (bombnum == 2) {
                    bombnum = 0;
                    bomb = image;
                }
                spriteBomb = 0;
            }
            g2.drawImage(bomb, worldX, worldY, gp.tileSize, gp.tileSize, null);
        } else explosion(g2);
    }
    private void explosion(Graphics2D g2){
        for(int i = 1; i < bomb_range_top; i++){
            g2.drawImage(bomb_explosion_row[1], worldX, worldY - i*gp.tileSize, gp.tileSize, gp.tileSize, null );
        }
        g2.drawImage(bomb_explosion_row[0], worldX, worldY - bomb_range_top*gp.tileSize, gp.tileSize, gp.tileSize, null );

        for(int i = 1; i < bomb_range_bot; i++){
            g2.drawImage(bomb_explosion_row[1], worldX, worldY  + i*gp.tileSize, gp.tileSize, gp.tileSize, null );
        }
        g2.drawImage(bomb_explosion_row[2], worldX, worldY + bomb_range_bot*gp.tileSize, gp.tileSize, gp.tileSize, null );

        for(int i = 1; i < bomb_range_left; i++){
            g2.drawImage(bomb_explosion_col[1], worldX - i*gp.tileSize, worldY, gp.tileSize, gp.tileSize, null );
        }
        g2.drawImage(bomb_explosion_col[0], worldX - bomb_range_left*gp.tileSize, worldY, gp.tileSize, gp.tileSize, null );

        for(int i = 1; i < bomb_range_right; i++){
            g2.drawImage(bomb_explosion_col[1], worldX + i*gp.tileSize, worldY, gp.tileSize, gp.tileSize, null );
        }
        g2.drawImage(bomb_explosion_col[2], worldX + bomb_range_right*gp.tileSize, worldY, gp.tileSize, gp.tileSize, null );

        g2.drawImage(image_center, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}
