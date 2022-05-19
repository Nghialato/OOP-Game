package main.tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNum;
    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        getTileImage();
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        loadMap("/asset/playerimage/map01.txt");
    }

    public void getTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/asset/playerimage/f602.png"));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/asset/playerimage/w202.png"));
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/asset/playerimage/objects2_61.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String link){
        try{
            InputStream is = getClass().getResourceAsStream(link);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0, row = 0;

            while(col <gp.maxScreenCol && row < gp.maxScreenRow){
                String line = br.readLine();

                while (col < gp.maxScreenCol){
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }

        } catch (Exception ignored){
        }
    }

    public void draw(Graphics2D g2){
        for(int x = 0; x < gp.maxScreenRow; x++){
            for(int y = 0; y < gp.maxScreenCol; y++){
                int tileNum = mapTileNum[y][x];
                if(tileNum < 3){
                    g2.drawImage(tile[tileNum].image, y*gp.tileSize, x*gp.tileSize, 16*gp.scale, 24*gp.scale, null);
                }
                else {
                    g2.drawImage(tile[0].image, y*gp.tileSize, x*gp.tileSize, 16*gp.scale, 24*gp.scale, null);
                    g2.drawImage(tile[tileNum].image, y*gp.tileSize, x*gp.tileSize, 16*gp.scale, 16*gp.scale, null);
                }
            }
        }
    }
}
