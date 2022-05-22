package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    public int screenX;
    public int screenY;

    public Player (GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth/2 - 25*gp.scale/2;
        screenY = gp.screenHeight/2 - 28*gp.scale/2;

        solidArea = new Rectangle(8, 16, 32, 32);

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
        worldX = 100;
        worldY = 100;
        flip = 1;
        speed = 4;
        direction = "right";
        bool = 1;
        right = new BufferedImage[10];
    }
    public void getPlayerImage(){
        try {
            for(int i = 0; i < 5; i++){
                right[i]= ImageIO.read(getClass().getResourceAsStream("/asset/playerimage/c01_" + i + ".png"));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void update(){
        if(keyH.upPressed){
            direction = "up";
            worldY -= speed;
        }
        if(keyH.downPressed){
            direction = "down";
            worldY+=speed;
        }
        if(keyH.leftPressed){
            direction = "left";
            if(bool == 1){
                screenX += 25 * gp.scale;
                bool--;
            }
            worldX-= speed;
        }
        if (keyH.rightPressed){
            direction = "right";
            if(bool == 0){
                screenX-= 25 * gp.scale;
                bool ++;
            }
            worldX+= speed;
        }
        if(keyH.downPressed || keyH.leftPressed || keyH.upPressed || keyH.rightPressed) {
            spriteCounter++;
        } else {if(spriteNum != 0){
            if(spriteNum == 3){
                spriteNum = 0;
            } else spriteNum++;
        }
        }
        if(spriteCounter > 5){
            if(spriteNum == 3){
                spriteNum = 0;
            } else {
                spriteNum++;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g){

        BufferedImage image = right[spriteNum];
        switch (direction) {
            case "left":
                flip = -1;
                break;
            case "right":
                flip = 1;
                break;
        }
        g.drawImage(image, screenX, screenY, 25 *flip * gp.scale, 28*gp.scale, null);
    }
}
