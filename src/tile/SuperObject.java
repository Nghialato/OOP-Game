package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class SuperObject extends Tile{

    protected String name;
    public String get_name(){return name;}
    protected boolean collision = false;

    public boolean isCollision() {
        return collision;
    }

    public int worldX, worldY;

    public Rectangle solidArea = new Rectangle(0,0 , 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public void draw(Graphics2D g2, GamePanel gp){
        g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);

    }

    public void SuperObject( int worldX, int worldY, String name){
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = name;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/" + name +  ".png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
