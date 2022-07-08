package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class E_Randomly extends Entity {

    GamePanel gp;
    Player player;
    int Direction;
    int time = 10000;
    int initial;

    public E_Randomly(GamePanel gp, Player player, int initial) {
        this.gp = gp;
        this.player = player;
        this.initial = initial;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;


        setDefaultValues();
        getImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize*initial;
        worldY = gp.tileSize*(14-initial);
        speed = 2;
        Direction=2;
    }

    public void getImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_right_2.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        Random rand = new Random();
        if(time > 60) {
            Direction = rand.nextInt(4)+1;
            switch(Direction){
            case 1:
                direction = "up";
                break;
            case 2:
                direction = "down";
                break;
            case 3:
                direction = "left";
                break;
            case 4:
                direction = "right";
                break;
            }
            time = 0;
        } else time++;

        collisionOn = false;
        gp.cChecker.checkTile(this);
        if (!collisionOn) {
            switch(direction){
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
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
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(Direction){
            case 1:
                direction = "up";
                break;
            case 2:
                direction = "down";
                break;
            case 3:
                direction = "left";
                break;
            case 4:
                direction = "right";
                break;
            }

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
        g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}