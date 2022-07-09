package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;


public class SuperObject {
    GamePanel gp;
    protected BufferedImage image;
    public BufferedImage get_image(){ return image;}

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
}
