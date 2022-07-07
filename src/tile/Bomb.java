package tile;

import entity.Player;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Bomb extends Tile {
    private int worldX, worldY, spriteBomb, bombnum = 0, bombrange = 2;
    BufferedImage bomb = null, imagetopbomb, imagetopbomb2, imagebotbomb2
            , imagerightbomb, imagerightbomb2, imageleftbomb2;
    public int time = 0;
    public void setWorldX(int worldX) {
        this.worldX = (worldX/gp.tileSize) * gp.tileSize;
    }

    public void setWorldY(int worldY) {
        this.worldY = (worldY/gp.tileSize + 1) * gp.tileSize;
    }
    public Player player;
    public GamePanel gp;
    public Bomb(Player player, GamePanel gp) {
        this.player = player;
        this.gp = gp;
        loadimg();
    }

    public void loadimg(){
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/bomb.png")));
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/bomb_1.png")));
            image2 = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/tiles/bomb_2.png"))));
            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/bomb_exploded.png")));
            imagetopbomb = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/explosion_vertical2.png")));
            imagerightbomb = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/tiles/explosion_horizontal2.png"))));
            imagetopbomb2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/explosion_vertical_top_last2.png")));
            imagebotbomb2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/explosion_vertical_down_last2.png")));
            imagerightbomb2 = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/tiles/explosion_horizontal_right_last2.png"))));
            imageleftbomb2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/explosion_horizontal_left_last2.png")));
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
        } else bomb = image3;
        g2.drawImage(bomb, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }

//    public void bombexplosion(Graphics2D g2){
//        BufferedImage explosion;
//        for(int i = 1; i <= bombrange; i++){
//            explosion
//            g2.drawImage(explosion, worldX, worldY + i * gp.tileSize, gp.tileSize, gp.tileSize, null);
//            g2.drawImage(explosion, worldX, worldY - i * gp.tileSize, gp.tileSize, gp.tileSize, null);
//        }
//
//    }

}
