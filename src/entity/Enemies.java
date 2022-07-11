package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemies extends Entity{
    protected Player player;
    protected int time = 10000;

    BufferedImage right1, left1, up1, up2;
    protected int delay_bite = 10000;
    protected BufferedImage image;

    public Enemies(Player player){
        this.player = player;
    }

    protected void P_bite (float range) {
        if (Math.abs(player.worldX - range * worldX) < solidArea.width
                &&  Math.abs(player.worldY - range * worldY) < solidArea.height) player.current_health--;
    }
}
