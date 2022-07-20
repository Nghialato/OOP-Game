package entity;

import main.GamePanel;
import tile.Bomb;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.util.Random;

public class E_Randomly extends Enemies {
    GamePanel gp;
    Player player;
    int time = 10000;
    int delay_bite = 10000;
    int initial;
    BufferedImage image;

    public E_Randomly(GamePanel gp, Player player, int initial) {
        super(player);
        this.gp = gp;
        this.player = player;
        this.initial = initial;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        image = null;
        setDefaultValues();
        getImage();
    }

    public void setDefaultValues() {
        name = "E";
        worldX = gp.tileSize*(initial+4);
        worldY = gp.tileSize*(14-initial);
        current_health = 1;
        speed = 2;
        Direction = 2;
        direction = "down";
    }
    public void update() {
        Random rand = new Random();
        if(time > 60) {
            Direction = rand.nextInt(4)+1;
            switch (Direction) {
                case 1 -> direction = "up";
                case 2 -> direction = "down";
                case 3 -> direction = "left";
                case 4 -> direction = "right";
            }
            time = 0;
        }
        time++;

        collisionOn = false;
        gp.cChecker.checkTile(this);

        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }

            spriteCounter++;
            if(spriteCounter>12){
                if(spriteNum == 1){
                    spriteNum = 2;

                } else if (spriteNum ==2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            time = 61;
        }
        if (delay_bite > 60) {

            P_bite(1.5F);
            delay_bite = 0;
        } else delay_bite++;
        for(Bomb bomb : player.bombs){
            if(bomb.time == 130) update_health(bomb, gp);
        }
    }
}
