package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;


public class SuperObject extends Tile{
    protected String name;
    private boolean visuable = false;
    public boolean isVisuable() {
        return visuable;
    }
    public void setVisuable(boolean visuable){
        this.visuable = visuable;
    }
    public String get_name(){return name;}
    protected boolean collision = false;

    public boolean isCollision() {
        return collision;
    }
    public Rectangle solidArea = new Rectangle(8,8 , 32, 32);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public void draw(Graphics2D g2, GamePanel gp){
        g2.drawImage(image, worldX, worldY, 48, 48, null);

    }
    public SuperObject( int worldX, int worldY, String name, GamePanel gp){
        this.worldX = worldX * gp.tileSize;
        this.worldY = worldY * gp.tileSize;
        this.name = name;
        this.gp = gp;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/" + name +  ".png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
