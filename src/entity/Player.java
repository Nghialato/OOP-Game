package entity;

import main.GamePanel;
import main.KeyHandler;
import tile.Bomb;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Key;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;
    private boolean move;
    Queue<Bomb> bombs = new LinkedList<>();
    private int maxbomb;


    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;


        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;


        setDefaultValues();
        getPlayerImage();

    }

    public void setDefaultValues() {
        worldX = gp.tileSize*13;
        worldY = gp.tileSize*13;
        maxbomb = 4;
        speed = 3;
        direction="down";
        move = true;
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
//        System.out.println("Space pressed " + keyH.spacePressed);
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
        } else if (keyH.spacePressed && maxbomb > bombs.size()){
            System.out.println("Bomb on map" + bombs.size());
            System.out.println("Max bomb "+maxbomb);
//          System.out.println("Test Space Press");
            Bomb bomb1 = new Bomb(this, gp);
//            bomb[bombonmap - 1].setWorldX(worldX + gp.tileSize/2);
//            bomb[bombonmap - 1].setWorldY(worldY - gp.tileSize/2);
            bomb1.setWorldX(worldX + gp.tileSize/2);
            bomb1.setWorldY(worldY - gp.tileSize/2);
            bombs.add(bomb1);
            move = false;
        }

        keyH.spacePressed = false;

        collisionOn = false;
        gp.cChecker.checkTile(this);

        if(!collisionOn && !keyH.spacePressed && move){

            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
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
    }

    public void draw(Graphics2D g2) {
       // g2.setColor(Color.white);
       // g2.fillRect(x,y,gp.tileSize,gp.tileSize);


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
            if(bombs.peek().time > 140) bombs.remove();
            for (Bomb bomb : bombs) {
                bomb.draw(g2);
            }
        }
        g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}