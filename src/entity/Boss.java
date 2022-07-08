package entity;

import main.GamePanel;
import tile.Bomb;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Boss extends Entity {

    GamePanel gp;
    Player player;
    int Direction;

    int LeftWorldX;
    int RightWorldX;
    int TopWorldY;
    int BottomWorldY;

    int LeftCol;
    int RightCol;
    int TopRow;
    int BottomRow;

    int time = 10000;
    int curMap[][];

    public Boss(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        curMap = new int[gp.maxScreenCol][gp.maxScreenRow];

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;


        setDefaultValues();
        getImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize*(gp.maxScreenCol-2);
        worldY = gp.tileSize*(gp.maxScreenRow-2);
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

    public void update_map() {
        for (int i=0; i<gp.maxScreenCol-1; i++) for (int j=0; j<gp.maxScreenRow-1; j++) curMap[i][j] = 0;

        for (Bomb element : player.bombs) {
            if (element != null) {
                int x = element.getWorldX()/gp.tileSize;
                int y = element.getWorldY()/gp.tileSize;
                curMap[x][y] = 2;
                int range = element.bomb_range;
                for (int j = 1; j <= range; j++) {
                    if (x+j < 15) curMap[x+j][y] = 1;
                    if (x-j >= 0) curMap[x-j][y] = 1;
                    if (y+j < 15) curMap[x][y+j] = 1;
                    if (y-j >= 0) curMap[x][y-j] = 1;
                }
            }
        }
    }

    boolean moveable () {
        boolean res = true;

        int tileNum1, tileNum2;

        switch(direction){
            case "up":
                TopRow = (TopWorldY - speed)/gp.tileSize;
                tileNum1 = curMap[LeftCol][TopRow];
                tileNum2 = curMap[RightCol][TopRow];
                if(tileNum1 == 1 || tileNum2 == 1){
                    res = false;
                }
                break;

            case "down":
                BottomRow = (BottomWorldY + speed)/gp.tileSize;
                tileNum1 = curMap[LeftCol][BottomRow];
                tileNum2 = curMap[RightCol][BottomRow];
                if(tileNum1 == 1 || tileNum2 == 1){
                    res = false;
                }
                break;

            case "left":
                LeftCol = (LeftWorldX - speed)/gp.tileSize;
                tileNum1 = curMap[LeftCol][TopRow];
                tileNum2 = curMap[LeftCol][BottomRow];
                if(tileNum1 == 1 || tileNum2 == 1){
                    res = false;
                }
                break;
            case "right":
                RightCol = (RightWorldX + speed)/gp.tileSize;
                tileNum1 = curMap[RightCol][TopRow];
                tileNum2 = curMap[RightCol][BottomRow];
                if(tileNum1 == 1 || tileNum2 == 1){
                    res = false;
                }
                break;
        }
        return res;
    }

    public void update() {
        LeftWorldX = worldX + solidArea.x;
        RightWorldX = worldX + solidArea.x + solidArea.width;
        TopWorldY = worldY + solidArea.y;
        BottomWorldY = worldY + solidArea.y + solidArea.height;

        LeftCol = LeftWorldX/gp.tileSize;
        RightCol = RightWorldX/gp.tileSize;
        TopRow = TopWorldY/gp.tileSize;
        BottomRow = BottomWorldY/gp.tileSize;

        update_map();
        Random rand = new Random();

        if (curMap[LeftCol][TopRow] == 1  && curMap[RightCol][BottomRow] == 0) {
            if (curMap[LeftCol][BottomRow] == 1) direction = "right";
            if (curMap[RightCol][TopRow] == 1) direction = "down";
        } 

        if (curMap[LeftCol][TopRow] == 0  && curMap[RightCol][BottomRow] == 1) {
            if (curMap[LeftCol][BottomRow] == 1) direction = "up";
            if (curMap[RightCol][TopRow] == 1) direction = "left";
        } 

        if (curMap[LeftCol][TopRow] == 1 && curMap[RightCol][BottomRow] == 1) {
            if (LeftCol % 2 == 1 && RightCol % 2 == 0) direction = "left";
            if (LeftCol % 2 == 0 && RightCol % 2 == 1) direction = "right";
            if (TopRow % 2 == 0 && BottomRow % 2 == 1) direction = "down";
            if (TopRow % 2 == 1 && BottomRow % 2 == 0) direction = "up";

            if (LeftCol % 2 == 0 && RightCol % 2 == 0) {
                Direction = rand.nextInt(2); if(Direction == 0) direction = "left"; else direction = "right";
                if (curMap[LeftCol-1][TopRow] == 2) direction = "right";
                if (curMap[LeftCol+1][TopRow] == 2) direction = "left";
            }
            if (TopRow % 2 == 0 && BottomRow % 2 == 0) {
                Direction = rand.nextInt(2); if(Direction == 0) direction = "up"; else direction = "down";
                if (curMap[LeftCol][TopRow-1] == 2) direction = "down";
                if (curMap[LeftCol][TopRow+1] == 2) direction = "up";
            }

            if (LeftCol % 2 == 1 && RightCol % 2 == 1
            &&  TopRow % 2 == 1 && BottomRow % 2 == 1) {
                Direction = rand.nextInt(4)+1;
                if (Direction == 1) if (curMap[LeftCol][TopRow-1] == 0) direction = "up";
                if (Direction == 2) if (curMap[LeftCol][TopRow+1] == 0) direction = "down";
                if (Direction == 3) if (curMap[LeftCol-1][TopRow] == 0) direction = "left";
                if (Direction == 4) if (curMap[LeftCol+1][TopRow] == 0) direction = "right";
            }

        }

        if (curMap[LeftCol][TopRow] == 0 && curMap[RightCol][BottomRow] == 0) {
            if(time > 60) {
                do {
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
                } while (!moveable());
            } else time++;
        }

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

