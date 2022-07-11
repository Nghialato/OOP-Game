package tile;

import entity.Player;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    Player player;
    public Tile[] tile;
    int count = 0;
    Bomb bomb;

    SuperObject[] obj = new SuperObject[8];
    public int[][] mapTileNum;
    public TileManager(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();

        loadmap("/maps/map02.txt");
    }
    public void loadmap(String filePath){

        try{
            InputStream is = getClass().getResourceAsStream((filePath));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxScreenCol && row < gp.maxScreenRow){

                String line = br.readLine();

                while( col < gp.maxScreenCol){

                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxScreenCol){
                    col = 0;
                    row++;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        for( int j = 0; j< gp.maxScreenRow; j++){
            for( int i = 0; i< gp.maxScreenCol; i++){
                if(mapTileNum[i][j]== 9){
                    mapTileNum[i][j] = 0;
                }
            }
        }
    }
    public void getTileImage(){
        bomb = new Bomb(player, gp);
        try{
            tile[0] = new Tile();
            tile[0].image =ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/floor_1.png")));
            tile[0].collision = false;

            tile[1] = new Tile();
            tile[1].image =ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));
            tile[1].explosion = 2;

            tile[2] = new Tile();
            tile[2].image =ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/brick_exploded.png")));
            tile[2].explosion = 1;

            tile[4] = new Tile();
            tile[4].image =ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/floor_spikes_anim_f2.png")));

            tile[6] = new Tile(); // A tile that likes grass but has collision when the player go out of its col or row
            tile[6].image = tile[0].image;

            tile[9] = new Tile();
            tile[9] = tile[0];

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void update(){
        int col, row;
        for(Bomb bomb : player.bombs){
            col = bomb.getWorldX()/gp.tileSize;
            row = bomb.getWorldY()/gp.tileSize;
            if ((player.getWorldX() + gp.tileSize/2)/gp.tileSize != bomb.getWorldX()/ gp.tileSize || (player.getWorldY() - gp.tileSize/2)/gp.tileSize + 1 != bomb.getWorldY()/gp.tileSize){
                mapTileNum[col][row] = 6;
            }
            if(bomb.time > 115 && bomb.time < 120) {
                System.out.println("Check draw " + count++);
                for(int i = 1; i <= Bomb.bomb_range; i++){
                    if(row - i >= 0){
                        if (tile[mapTileNum[col][row - i]].explosion == 1 && bomb.bomb_range_top <= Bomb.bomb_range) {
                            bomb.bomb_range_top++;
                            break;
                        }
                        else if(tile[mapTileNum[col][row - i]].explosion == 0 && bomb.bomb_range_top <= Bomb.bomb_range){
                            bomb.bomb_range_top++;
                        } else break;
                    } else break;
                }
                for(int i = 1; i <= Bomb.bomb_range; i++){
                    if(row + i < gp.maxScreenRow){
                        if (tile[mapTileNum[col][row + i]].explosion == 1 && bomb.bomb_range_bot <= Bomb.bomb_range) {
                            bomb.bomb_range_bot++;
                            break;
                        }
                        else if(tile[mapTileNum[col][row + i]].explosion == 0 && bomb.bomb_range_bot <= Bomb.bomb_range){
                            bomb.bomb_range_bot++;
                        } else break;
                    } else break;
                }
                for(int i = 1; i <= Bomb.bomb_range; i++){
                    if(col - i >= 0){
                        if (tile[mapTileNum[col - i][row]].explosion == 1 && bomb.bomb_range_left <= Bomb.bomb_range) {
                            bomb.bomb_range_left++;
                            break;
                        }
                        else if(tile[mapTileNum[col - i][row]].explosion == 0 && bomb.bomb_range_left <= Bomb.bomb_range){
                            bomb.bomb_range_left++;
                        } else break;
                    } else break;
                }
                for(int i = 1; i <= Bomb.bomb_range; i++){
                    if(col + i < gp.maxScreenCol){
                        if (tile[mapTileNum[col + i][row]].explosion == 1 && bomb.bomb_range_right <= Bomb.bomb_range) {
                            bomb.bomb_range_right++;
                            break;
                        }
                        else if(tile[mapTileNum[col + i][row]].explosion == 0 && bomb.bomb_range_right <= Bomb.bomb_range){
                            bomb.bomb_range_right++;
                        } else break;
                    } else break;
                }
                bomb.time = 120;
            }
            if(bomb.time > 120) {
                mapTileNum[col][row] = 0;
                for(int i = 1; i <= bomb.bomb_range_top; i++){
                    mapTileNum[col][row-i] = 0;
                }
                for(int i = 1; i <= bomb.bomb_range_bot; i++){
                    mapTileNum[col][row+i] = 0;
                }
                for(int i = 1; i <= bomb.bomb_range_left; i++){
                    mapTileNum[col - i][row] = 0;
                }
                for(int i = 1; i <= bomb.bomb_range_right; i++){
                    mapTileNum[col + i][row] = 0;
                }
            } // Set up to draw image explosion in range. Can be use for check in range.
        }
    }
    public void draw(Graphics2D g2){
        int col = 0 ;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col <gp.maxScreenCol && row <gp.maxScreenRow){
            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if(col == gp.maxScreenCol){
                col = 0;
                x=0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}