package entity;

import main.GamePanel;
import tile.Bomb;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Random;

public class Boss extends Enemies {
    public static boolean active;
    protected BufferedImage no_active;
    int[][] curMap;
    boolean[][] prior;
    public Boss(GamePanel gp, Player player) {
        super(player);
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
        name = "boss";
        worldX = gp.tileSize*(gp.maxScreenCol-2);
        worldY = gp.tileSize*(gp.maxScreenRow-2);
        current_health = 10;
        speed = 2;
        Direction = 1;
        direction = "up";
    }

    public void getImage() {
        super.getImage();
        try {
            no_active = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/boss/B_NoActive.png")));
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
                int range = Bomb.bomb_range;
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

    boolean moveable () {
        boolean res = true;
        int LeftWorldX = worldX + solidArea.x;
        int RightWorldX = worldX + solidArea.x + solidArea.width;
        int TopWorldY = worldY + solidArea.y;
        int BottomWorldY = worldY + solidArea.y + solidArea.height;

        int LeftCol = LeftWorldX / gp.tileSize;
        int RightCol = RightWorldX / gp.tileSize;
        int TopRow = TopWorldY / gp.tileSize;
        int BottomRow = BottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (direction) {
            case "up" -> {
                TopRow = (TopWorldY - speed) / gp.tileSize;
                tileNum1 = curMap[LeftCol][TopRow];
                tileNum2 = curMap[RightCol][TopRow];
                if (tileNum1 >= 1 || tileNum2 >= 1) {
                    res = false;
                }
            }
            case "down" -> {
                BottomRow = (BottomWorldY + speed) / gp.tileSize;
                tileNum1 = curMap[LeftCol][BottomRow];
                tileNum2 = curMap[RightCol][BottomRow];
                if (tileNum1 >= 1 || tileNum2 >= 1) {
                    res = false;
                }
            }
            case "left" -> {
                LeftCol = (LeftWorldX - speed) / gp.tileSize;
                tileNum1 = curMap[LeftCol][TopRow];
                tileNum2 = curMap[LeftCol][BottomRow];
                if (tileNum1 >= 1 || tileNum2 >= 1) {
                    res = false;
                }
            }
            case "right" -> {
                RightCol = (RightWorldX + speed) / gp.tileSize;
                tileNum1 = curMap[RightCol][TopRow];
                tileNum2 = curMap[RightCol][BottomRow];
                if (tileNum1 >= 1 || tileNum2 >= 1) {
                    res = false;
                }
            }
        }
        return res;
    }

    public void update() {
        int leftWorldX = worldX + solidArea.x;
        int rightWorldX = worldX + solidArea.x + solidArea.width;
        int topWorldY = worldY + solidArea.y;
        int bottomWorldY = worldY + solidArea.y + solidArea.height;

        int leftCol = leftWorldX / gp.tileSize;
        int rightCol = rightWorldX / gp.tileSize;
        int topRow = topWorldY / gp.tileSize;
        int bottomRow = bottomWorldY / gp.tileSize;
    
        update_map();
        Random rand = new Random();

        if (curMap[leftCol][topRow] > 1  && curMap[rightCol][bottomRow] == 0) {
            if (curMap[leftCol][bottomRow] > 1) direction = "right";
            if (curMap[rightCol][topRow] > 1) direction = "down";
        } 

        if (curMap[leftCol][topRow] == 0  && curMap[rightCol][bottomRow] > 1) {
            if (curMap[leftCol][bottomRow] > 1) direction = "up";
            if (curMap[rightCol][topRow] > 1) direction = "left";
        } 

        if (curMap[leftCol][topRow] > 1 && curMap[rightCol][bottomRow] > 1) {
            if (leftCol == rightCol && topRow == bottomRow) {
                if (prior[leftCol + 1][topRow] && curMap[leftCol +1][topRow] != 2) direction = "right";
                if (prior[leftCol - 1][topRow] && curMap[leftCol -1][topRow] != 2) direction = "left";
                if (prior[leftCol][topRow + 1] && curMap[leftCol][topRow +1] != 2) direction = "down";
                if (prior[leftCol][topRow - 1] && curMap[leftCol][topRow +1] != 2) direction = "up";

                if (curMap[leftCol +1][topRow] == 0) direction = "right";
                if (curMap[leftCol -1][topRow] == 0) direction = "left";
                if (curMap[leftCol][topRow +1] == 0) direction = "down";
                if (curMap[leftCol][topRow -1] == 0) direction = "up";
            }
            if (leftCol == rightCol && topRow != bottomRow) {
                if (prior[leftCol][topRow] && curMap[leftCol][topRow] != 2) direction = "up";
                if (prior[leftCol][bottomRow] && curMap[leftCol][bottomRow] != 2) direction = "down";

                if (curMap[leftCol +1][topRow] == 0) direction = "up";
                if (curMap[leftCol -1][topRow] == 0) direction = "up";
                if (curMap[leftCol +1][bottomRow] == 0) direction = "down";
                if (curMap[leftCol -1][bottomRow] == 0) direction = "down";

                if (curMap[leftCol][topRow -1] == 0) direction = "down";
                if (curMap[leftCol][bottomRow +1] == 0) direction = "up";
            }
            if (leftCol != rightCol && topRow == bottomRow) {
                if (prior[leftCol][topRow] && curMap[leftCol][topRow] != 2) direction = "left";
                if (prior[rightCol][topRow] && curMap[rightCol][topRow] != 2) direction = "right";

                if (curMap[leftCol][topRow +1] == 0) direction = "left";
                if (curMap[leftCol][topRow -1] == 0) direction = "left";
                if (curMap[rightCol][topRow +1] == 0) direction = "right";
                if (curMap[rightCol][topRow -1] == 0) direction = "right";

                if (curMap[leftCol -1][topRow] == 0) direction = "left";
                if (curMap[rightCol +1][topRow] == 0) direction = "right";
            }
            if (leftCol != rightCol && topRow != bottomRow) {
                if (prior[leftCol][topRow] && curMap[leftCol][topRow] != 2) direction = "up";
                if (prior[leftCol][bottomRow] && curMap[leftCol][bottomRow] != 2) direction = "left";
                if (prior[rightCol][bottomRow] && curMap[rightCol][bottomRow] != 2) direction = "down";
                if (prior[rightCol][topRow] && curMap[rightCol][topRow] != 2) direction = "right";

                if (curMap[leftCol][topRow] == 0) direction = "up";
                if (curMap[leftCol][bottomRow] == 0) direction = "left";
                if (curMap[rightCol][bottomRow] == 0) direction = "down";
                if (curMap[rightCol][topRow] == 0) direction = "right";

            }
        }

        if (curMap[leftCol][topRow] == 0 && curMap[rightCol][bottomRow] == 0) {
            if(time > 30) {
                    Direction = rand.nextInt(4)+1;
                switch (Direction) {
                    case 1 -> direction = "up";
                    case 2 -> direction = "down";
                    case 3 -> direction = "left";
                    case 4 -> direction = "right";
                }
                time = 0;
                if(!moveable()) time = 61;
            }
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
        if (delay_bite > 30) {
            P_bite(1);
            delay_bite = 0;
        } else delay_bite++;
        for(Bomb bomb : player.bombs){
            if(bomb.time == 130) update_health(bomb, gp);
        }
    }

    public void draw(Graphics2D g2) {
        if (current_health == 0) active = false;
        if (!active) {
            image = no_active;
            g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
        } else super.draw(g2);
    }

}

