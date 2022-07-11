package entity;

import main.GamePanel;
import main.KeyHandler;
import tile.Bomb;
import tile.SuperObject;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    private boolean move;
    public Queue<Bomb> bombs = new LinkedList<>();
    private int maxbomb;

    public int getMaxbomb(){return  maxbomb;}


    private boolean activated = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }


    public void setDefaultValues() {
        worldX = gp.tileSize;
        worldY = gp.tileSize;

        max_health = 5;
        current_health = max_health;
        maxbomb = 2;
        speed = 3;
        direction= "down";
        move = true;

/*
        hasKey = 0;*/
    }

/*
    protected int hasKey;
*/



    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_2.png")));



        } catch (IOException e) {
            e.printStackTrace();
        }
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
            Bomb bomb1 = new Bomb(this, gp);
            bomb1.setWorldX(worldX + gp.tileSize/2);
            bomb1.setWorldY(worldY - gp.tileSize/2);
            bombs.add(bomb1);
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

        if(move){
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;

                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

            if (current_health <= 0) {
                gp.gameState = gp.gameOverState;
            }
        }
        for(Bomb bomb : bombs){
            if(bomb.time == 130) update_health(bomb, gp);
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {

            String objectName = gp.obj[i].get_name();

            switch (objectName) {
                case "shoe":
                    gp.obj[i] = null;
                    speed += 1;
                    break;

                case "chest":
                    gp.obj[i] = null;

                    Random rand = new Random();
                    int object = rand.nextInt(7) + 1;
                    switch (object) {
                        case 1 -> speed++;
                        case 2 -> current_health++;
                        case 3 -> maxbomb++;
                        case 4 -> maxbomb--;
                        case 5 -> speed--;
                        case 6 -> current_health--;
                    }
                    break;


                case "bomb":
                    gp.obj[i] = null;
                    maxbomb += 1;
                    break;
                case "heart":
                    gp.obj[i] = null;
                    current_health += 1;
                    break;
                case "door":
                    if (i==4){
                        worldX = gp.obj[1].worldX;
                        worldY = gp.obj[1].worldY + gp.tileSize;
                    }
                    if (i==1){
                        worldX = gp.obj[4].worldX + gp.tileSize;
                        worldY = gp.obj[4].worldY;
                    }
                    break;

            }

        }
    }


    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
            }
        }
        if(bombs.size() > 0 && bombs.size() <= maxbomb){
            if(bombs.peek().time > 150) bombs.remove();
            for (Bomb bomb : bombs) {
                bomb.draw(g2);
            }
        }
        g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }

}