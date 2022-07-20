package entity;

import main.GamePanel;
import main.KeyHandler;
import tile.Bomb;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;

public class Player extends Entity {
    KeyHandler keyH;
    private boolean move;
    public Queue<Bomb> bombs = new LinkedList<>();
    private int maxbomb;

    private int count_minus ;
    public int getMaxbomb() {
        return maxbomb;
    }

    private boolean activated = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        solidArea = new Rectangle();
        solidArea.x = 10;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 28;
        solidArea.height = 28;
        activated = false;

        setDefaultValues();
        getImage();
    }

    public void setDefaultValues() {
        name = "player";
        worldX = gp.tileSize;
        worldY = gp.tileSize;
        max_health = 5;
        current_health = 5;
        maxbomb = 2;
        speed = 2;
        direction= "down";
        minus_health = false;
        move = true;
        count_minus = 0;
    }

    public void B_activate() {
        if (worldX > 14*gp.tileSize && worldY > 10*gp.tileSize && !activated) {
            Boss.active = true;
            activated = true;
        }
    }

    public void update() {
        B_activate();
        if (keyH.upPressed) {
            direction = "up";
            move = true;
        } else if (keyH.downPressed) {
            direction = "down";
            move = true;
        } else if (keyH.leftPressed) {
            direction = "left";
            move = true;
        } else if (keyH.rightPressed) {
            direction = "right";
            move = true;
        } else move = false;
        if (keyH.spacePressed && maxbomb > bombs.size()){
            int count = 0;
            Bomb bomb1 = new Bomb(this, gp);
            bomb1.setWorldX(worldX + gp.tileSize/2);
            bomb1.setWorldY(worldY - gp.tileSize/2);
            for(Bomb bomb : bombs){
                if(bomb.getWorldX() == bomb1.getWorldX() && bomb.getWorldY() == bomb1.getWorldY()) count++;
            }
            if(count == 0) bombs.add(bomb1);
            move = false;
        }

        keyH.spacePressed = false;

        collisionOn = false;

        gp.cChecker.checkTile(this);

        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        if(!collisionOn && move){

            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        if(move && !minus_health){
            spriteCounter++;
            if (spriteCounter > 8) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else spriteNum = 1;
                spriteCounter = 0;
            }

            if (current_health <= 0) {
                gp.gameState = gp.gameOverState;
            }
        }

        if(minus_health && count_minus < 65){
            count_minus ++;
            spriteCounter++;
            if (spriteCounter > 4) {
                if (spriteNum == 1) {
                    spriteNum = 2;

                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if(spriteNum == 3){
                    spriteNum = 4;
                } else spriteNum = 1;
                spriteCounter = 0;
            }
            if (current_health <= 0) {
                gp.gameState = gp.gameOverState;
            }
        }

        if(count_minus > 60) {
            count_minus = 0;
            minus_health = false;
            move = true;
            spriteCounter = 100;
        }

        for(Bomb bomb : bombs){
            if(bomb.time > 125 && bomb.time < 130) {
                update_health(bomb, gp);
                bomb.time = 130;
            }
            for(Bomb bomb1 : bombs){
                if(bomb != bomb1) bomb.check_in_range(bomb1);
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {

            String objectName = gp.obj[i].get_name();

            switch (objectName) {
                case "shoe" -> {
                    gp.obj[i] = null;
                    speed += 1;
                    //gp.playSE(2);
                }
                case "chest" -> {
                    gp.obj[i] = null;
                    Random rand = new Random();
                    int object = rand.nextInt(7) + 1;
                    switch (object) {
                        case 1 -> speed++;
                        case 2 -> {
                            if(current_health <= max_health - 1){
                                current_health++;
                            }
                        }
                        case 3 -> maxbomb++;
                        case 4 -> maxbomb--;
                        case 5 -> speed--;
                        case 6 -> current_health--;
                        case 7 -> Bomb.bomb_range++;

                    }
                    //gp.playSE(2);
                }
                case "bomb" -> {
                    gp.obj[i] = null;
                    //gp.playSE(2);
                    maxbomb += 1;
                }
                case "heart" -> {
                    gp.obj[i] = null;
                    if(current_health <= max_health - 1){
                        current_health++;
                    }
                    //gp.playSE(2);
                }
                case "door" -> {
                    if (i == 4) {
                        worldX = gp.obj[1].getWorldX();
                        worldY = gp.obj[1].getWorldY() + gp.tileSize;
                    }
                    if (i == 1) {
                        worldX = gp.obj[4].getWorldX() + gp.tileSize;
                        worldY = gp.obj[4].getWorldY();
                    }
                }
                case "bomb_1" -> {

                    gp.obj[i] = null;
                    //gp.playSE(2);
                    Bomb.bomb_range++;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        if(bombs.size() > 0 && bombs.size() <= maxbomb){
            if(bombs.peek().time > 150) bombs.remove();
            for (Bomb bomb : bombs) {
                bomb.draw(g2);
            }
        }
        super.draw(g2);
    }
}