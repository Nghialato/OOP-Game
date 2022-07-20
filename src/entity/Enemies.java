package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemies extends Entity{
    protected Player player;
    protected int time = 10000;

    protected int delay_bite = 10000;
    protected BufferedImage image;

    public Enemies(Player player){
        this.player = player;
    }

    protected void P_bite (float range) {
        if (Math.abs(player.worldX - worldX) < (float) solidArea.width * range
                &&  Math.abs(player.worldY - worldY) < (float)solidArea.height * range) {
            player.current_health--;
            player.minus_health = true;
        }
    }
}
