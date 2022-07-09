package entity;

import main.GamePanel;
import tile.Bomb;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

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

    public static boolean active;
    BufferedImage no_active, image;
    int time = 10000;
    int delay_bite = 10000;
    int curMap[][];
    boolean prior[][];
   

    public Boss(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        curMap = new int[gp.maxScreenCol][gp.maxScreenRow];
        prior = new boolean[gp.maxScreenCol][gp.maxScreenRow];

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        image = no_active;
        setDefaultValues();
        getImage();
    }

    public void setDefaultValues() {
        active = false;
        worldX = gp.tileSize*(gp.maxScreenCol-2);
        worldY = gp.tileSize*(gp.maxScreenRow-2);
        current_health = 5;
        speed = 2;
        Direction=1;
    }

    public void getImage() {
        try {
            no_active = ImageIO.read(getClass().getResourceAsStream("/enermy/B_NoActive.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/enermy/B_static1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/enermy/B_static2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/enermy/B_left.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/enermy/B_right.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void base_map() {
        try{
            InputStream is = getClass().getResourceAsStream("/maps/map02.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxScreenCol && row < gp.maxScreenRow){

                String line = br.readLine();

                while( col < gp.maxScreenCol){

                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    curMap[col][row] = num;
                    col++;
                }
                if(col == gp.maxScreenCol){
                    col = 0;
                    row++;
                }
            }

            } catch (Exception e) {
            e.printStackTrace();
        }
        for (int j=0; j<gp.maxScreenRow; j++) for (int i=0; i<gp.maxScreenCol; i++) {
            if (curMap[i][j] == 9) prior[i][j] = true;
            if (curMap[i][j] >= 2) curMap[i][j] = 0;
        }
    }

    public void update_map() {
        base_map();

        for (Bomb element : player.bombs) {
            if (element != null) {
                int i;
                int x = element.getWorldX()/gp.tileSize;
                int y = element.getWorldY()/gp.tileSize;
                curMap[x][y] = 2;
                int range = element.bomb_range;
                i = 1;
                while (i <= range) {
                    if (curMap[x+i][y] == 1) break;
                    curMap[x+i][y] = 3;
                    i++;
                }
                i = 1;
                while (i <= range) {
                    if (curMap[x-i][y] == 1) break;
                    curMap[x-i][y] = 3;
                    i++;
                }
                i = 1;
                while (i <= range) {
                    if (curMap[x][y+i] == 1) break;
                    curMap[x][y+i] = 3;
                    i++;
                }
                i = 1;
                while (i <= range) {
                    if (curMap[x][y-i] == 1) break;
                    curMap[x][y-i] = 3;
                    i++;
                }
            }
        }
    }

    /*boolean moveable () {
        boolean res = true;

        int tileNum1, tileNum2;

        switch(direction){
            case "up":
                TopRow = (TopWorldY - speed)/gp.tileSize;
                tileNum1 = curMap[LeftCol][TopRow];
                tileNum2 = curMap[RightCol][TopRow];
                if(tileNum1 >= 1 || tileNum2 >= 1){
                    res = false;
                }
                break;

            case "down":
                BottomRow = (BottomWorldY + speed)/gp.tileSize;
                tileNum1 = curMap[LeftCol][BottomRow];
                tileNum2 = curMap[RightCol][BottomRow];
                if(tileNum1 >= 1 || tileNum2 >= 1){
                    res = false;
                }
                break;

            case "left":
                LeftCol = (LeftWorldX - speed)/gp.tileSize;
                tileNum1 = curMap[LeftCol][TopRow];
                tileNum2 = curMap[LeftCol][BottomRow];
                if(tileNum1 >= 1 || tileNum2 >= 1){
                    res = false;
                }
                break;
            case "right":
                RightCol = (RightWorldX + speed)/gp.tileSize;
                tileNum1 = curMap[RightCol][TopRow];
                tileNum2 = curMap[RightCol][BottomRow];
                if(tileNum1 >= 1 || tileNum2 >= 1){
                    res = false;
                }
                break;
        }
        return res;
    }*/

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

        if (curMap[LeftCol][TopRow] > 1  && curMap[RightCol][BottomRow] == 0) {
            if (curMap[LeftCol][BottomRow] > 1) direction = "right";
            if (curMap[RightCol][TopRow] > 1) direction = "down";
        } 

        if (curMap[LeftCol][TopRow] == 0  && curMap[RightCol][BottomRow] > 1) {
            if (curMap[LeftCol][BottomRow] > 1) direction = "up";
            if (curMap[RightCol][TopRow] > 1) direction = "left";
        } 

        if (curMap[LeftCol][TopRow] > 1 && curMap[RightCol][BottomRow] > 1) {
            if (LeftCol == RightCol && TopRow == BottomRow) {
                if (prior[LeftCol+1][TopRow] == true && curMap[LeftCol+1][TopRow] != 2) direction = "right";
                if (prior[LeftCol-1][TopRow] == true && curMap[LeftCol-1][TopRow] != 2) direction = "left";
                if (prior[LeftCol][TopRow+1] == true && curMap[LeftCol][TopRow+1] != 2) direction = "down";
                if (prior[LeftCol][TopRow-1] == true && curMap[LeftCol][TopRow+1] != 2) direction = "up";

                if (curMap[LeftCol+1][TopRow] == 0) direction = "right";
                if (curMap[LeftCol-1][TopRow] == 0) direction = "left";
                if (curMap[LeftCol][TopRow+1] == 0) direction = "down";
                if (curMap[LeftCol][TopRow-1] == 0) direction = "up";
            }
            if (LeftCol == RightCol && TopRow != BottomRow) {
                if (prior[LeftCol][TopRow] == true && curMap[LeftCol][TopRow] != 2) direction = "up";
                if (prior[LeftCol][BottomRow] == true && curMap[LeftCol][BottomRow] != 2) direction = "down";

                if (curMap[LeftCol+1][TopRow] == 0) direction = "up";
                if (curMap[LeftCol-1][TopRow] == 0) direction = "up";
                if (curMap[LeftCol+1][BottomRow] == 0) direction = "down";
                if (curMap[LeftCol-1][BottomRow] == 0) direction = "down";

                if (curMap[LeftCol][TopRow-1] == 0) direction = "down";
                if (curMap[LeftCol][BottomRow+1] == 0) direction = "up";
            }
            if (LeftCol != RightCol && TopRow == BottomRow) {
                if (prior[LeftCol][TopRow] == true && curMap[LeftCol][TopRow] != 2) direction = "left";
                if (prior[RightCol][TopRow] == true && curMap[RightCol][TopRow] != 2) direction = "right";

                if (curMap[LeftCol][TopRow+1] == 0) direction = "left";
                if (curMap[LeftCol][TopRow-1] == 0) direction = "left";
                if (curMap[RightCol][TopRow+1] == 0) direction = "right";
                if (curMap[RightCol][TopRow-1] == 0) direction = "right";

                if (curMap[LeftCol-1][TopRow] == 0) direction = "left";
                if (curMap[RightCol+1][TopRow] == 0) direction = "right";
            }
            if (LeftCol != RightCol && TopRow != BottomRow) {
                if (prior[LeftCol][TopRow] == true && curMap[LeftCol][TopRow] != 2) direction = "up";
                if (prior[LeftCol][BottomRow] == true && curMap[LeftCol][BottomRow] != 2) direction = "left";
                if (prior[RightCol][BottomRow] == true && curMap[RightCol][BottomRow] != 2) direction = "down";
                if (prior[RightCol][TopRow] == true && curMap[RightCol][TopRow] != 2) direction = "right";

                if (curMap[LeftCol][TopRow] == 0) direction = "up";
                if (curMap[LeftCol][BottomRow] == 0) direction = "left";
                if (curMap[RightCol][BottomRow] == 0) direction = "down";
                if (curMap[RightCol][TopRow] == 0) direction = "right";

            }
        }

        if (curMap[LeftCol][TopRow] == 0 && curMap[RightCol][BottomRow] == 0) {
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
            }
        }
        time++;

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
        if (delay_bite > 30) {
            P_bite();
            delay_bite = 0;
        } else delay_bite++;
    }

    public void draw(Graphics2D g2) {

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
                
                break;
            case "down":
                
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;}
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;}
                if(spriteNum == 2){
                    image = up1;
                }
                break;
        }
        update_health();
        if (current_health == 0) active = false;
        if (active == false) image = no_active;

        g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }

    private void update_health(){
        for(Bomb bomb : player.bombs){
            if(bomb.isExploding()){
                if (check_in_range(bomb, gp) && bomb.time == 130){
                    current_health--;
                }
            }
        }
    }

    public void P_bite () {
        if (Math.abs(player.worldX - this.worldX) < solidArea.width
        &&  Math.abs(player.worldY - this.worldY) < solidArea.height && time > 60) player.current_health--;
    }
}

