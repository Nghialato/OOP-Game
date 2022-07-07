package entity;

import main.GamePanel;
import main.KeyHandler;
import tile.Bomb;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Key;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;
    Bomb[] bomb;
    private int bombonmap, maxbomb;
    private boolean move;




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
        x = gp.tileSize*13;
        y = gp.tileSize*13;
        bombonmap = 0;
        maxbomb = 4;
        bomb = new Bomb[maxbomb];
        for(int i = 0; i < maxbomb; i++){
            bomb[i] = new Bomb(this, gp);
            System.out.println("Test collision " + bomb[i].collision);
            bomb[i].collision = true;
        }
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
        if (keyH.upPressed == true) {
            direction = "up";
        } else if (keyH.downPressed == true) {
            direction = "down";
        } else if (keyH.leftPressed == true) {
            direction = "left";
        } else if (keyH.rightPressed == true) {
            direction = "right";
        }
        else if (keyH.spacePressed && maxbomb > bombonmap){
        bombonmap++;
        System.out.println("Bomb on map" + bombonmap);
        System.out.println("Max bomb "+maxbomb);
//          System.out.println("Test Space Press");
        bomb[bombonmap - 1].setWorldX(x + gp.tileSize/2);
        bomb[bombonmap - 1].setWorldY(y - gp.tileSize/2);
        move = false;
        }

        keyH.spacePressed = false;

        collisionOn = false;
        gp.cChecker.checkTile(this);

        collisionOn = false;
        gp.cChecker.checkTile(this);

        //check object collision
        int objIndex = gp.cChecker.checkObject(this, true);

        pickUpObject(objIndex);

        if(collisionOn == false){

            switch(direction){
                case "up":
                    y -= speed;
                    break;
                case "down":
                    y += speed;
                    break;
                case "left":
                    x -= speed;
                    break;
                case "right":
                    x += speed;
                    break;
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

    public void pickUpObject(int i){
        if(i != 999){

            String objectName = gp.obj[i].name;

            switch (objectName){
                case "Shoe":
                    gp.obj[i] = null;
                    speed += 1 ;
                    break;
                case "Door":
                    if(i == 1) {
                        x = 120;
                        y = 630;
                        break;
                    }
                    if(i == 4) {
                        x = 650;
                        y = 90;
                        break;
                    }
            }

        }

    }



    public void draw(Graphics2D g2) {
       // g2.setColor(Color.white);
       // g2.fillRect(x,y,gp.tileSize,gp.tileSize);


        BufferedImage image = null;

        switch (direction) {
            case "up":
                if(spriteNum ==1 ){
                image = up1;}
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum ==1 ){
                image = down1;}
                if(spriteNum == 2){
                image = down2;
                }
                break;
            case "left":
                if(spriteNum ==1 ){
                    image = left1;}
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum ==1 ){
                    image = right1;}
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }
        if(bombonmap > 0 && bombonmap <= maxbomb){
            for(int i = 0; i < bombonmap; i++){
                bomb[i].draw(g2);
            }
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}