package tile;

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
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        loadMap("/asset/map/world1.txt");
    }

    public void getTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/asset/objects/f602.png"));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/asset/objects/w202.png"));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/asset/objects/objects2_61.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String link){
        try{
            InputStream is = getClass().getResourceAsStream(link);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0, row = 0;

            while(col <gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();

                while (col < gp.maxWorldCol){
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }

        } catch (Exception ignored){
        }
    }

    public void draw(Graphics2D g2){
        for(int x = 0; x < gp.maxWorldRow; x++){
            for(int y = 0; y < gp.maxWorldCol; y++){
                int tileNum = mapTileNum[y][x];

                int worldX = y * gp.tileSize;
                int worldY = x * gp.tileSize;
                int screenX = worldX - gp.player.worldX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;
                if(tileNum != 1){
                    g2.drawImage(tile[tileNum].image, screenX, screenY, 16*gp.scale, 16*gp.scale, null);
                } else {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, 16*gp.scale, 24*gp.scale, null);
                }
            }
        }
    }
}
