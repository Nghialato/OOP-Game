package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


import javax.imageio.ImageIO;


public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    BufferedImage image, image3, image4, image5, image6, image7;
    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;

        /*g2.setFont( arial_40);
        g2.setColor(Color.yellow);
        g2.drawImage(heartImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x " + gp.player.getCurrent_health(), 74, 65);*/

        if(gp.gameState == gp.playState){
            drawHeart();
            drawPlayerLife();
            drawShoe();
            drawPlayerSpeed();
            drawMaxBomb();
            drawPlayerBoom();
        }
        if(gp.gameState == gp.pauseState)
        {
            drawPauseScreen();
        }
        if(gp.gameState == gp.gameOverState)
        {   
            drawOverScreen();
        }
        if(gp.gameState == gp.playerStatus)
        {
            drawStatusScreen();
        }


    }


    public void drawHeart(){
        int x = gp.tileSize ;
        int y = gp.tileSize / 8;
        try {
            image4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
        g2.drawImage(image4, x, y, gp.tileSize, gp.tileSize, null);
    }

    public void drawShoe(){
        int x = gp.tileSize*8 ;
        int y = gp.tileSize /8;
        try {
            image5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/shoe.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
        g2.drawImage(image5, x, y, gp.tileSize, gp.tileSize, null);
    }

    public void drawMaxBomb(){
        int x = gp.tileSize*15 ;
        int y = gp.tileSize / 8;
        try {
            image7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/bomb.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
        g2.drawImage(image7, x, y, gp.tileSize, gp.tileSize, null);
    }


    public void drawPlayerLife() {
        int x = gp.tileSize * 5/2;
        int y = gp.tileSize / 4;
        int i = 0;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/bar_health.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
        while (i < gp.player.getCurrent_health()) {
            g2.drawImage(image, x, y, null);
            i++;
            x+=gp.tileSize/4;
        }
    }
    public void drawPlayerSpeed() {
        int x = gp.tileSize*8 + gp.tileSize*3/2 ;
        int y = gp.tileSize / 4;
        int i = 0;
        try {
            image6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/bar_speed.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
        while (i < gp.player.getSpeed()) {
            g2.drawImage(image6, x, y, null);
            i++;
            x+=gp.tileSize/4;
        }
    }


    public void drawPlayerBoom() {
        int x = gp.tileSize*16+gp.tileSize/2 ;
        int y = gp.tileSize / 4;
        int i = 0;
        try {
            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/bar_boom.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
        while (i < gp.player.getMaxbomb()) {
            g2.drawImage(image3, x, y, null);
            i++;
            x+=gp.tileSize/4;
        }
    }
    public void drawPauseScreen() {
        g2.setFont(new Font("Pixeboy", Font.PLAIN, 90));
        g2.setColor(Color.green);
        String text = "Paused";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public void drawOverScreen() {
        g2.setFont(new Font("Pixeboy", Font.PLAIN, 120));
        g2.setColor(Color.blue);
        String text = "Game Over";
        int x = getXforCenteredText(text);
        int y = (gp.screenHeight / 2)-50;
        g2.drawString(text, x, y);
        text = "press R back menu";
        x = getXforCenteredText(text);
        y = (gp.screenHeight / 2)+50;
        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text){
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }

    /*public void drawScreen(){
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - gp.tileSize*4;
        int height = gp.tileSize*4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
        x += gp.tileSize;
        y += gp.tileSize;;
    }*/


    public void drawStatusScreen(){
        final int frameX = 0;
        final int frameY = 0;
        final int frameWidth = gp.tileSize*4;
        final int frameHeight = gp.tileSize*3;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20F));
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 32;

        g2.drawString("Heart ", textX, textY);
        textY += lineHeight;
        g2.drawString("Max Bomb ", textX, textY);
        textY += lineHeight;
        g2.drawString("Speed ", textX, textY);

        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.getCurrent_health());
        textX = getXforAlignRightText(value,tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getMaxbomb());
        textX = getXforAlignRightText(value,tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getSpeed());
        textX = getXforAlignRightText(value,tailX);
        g2.drawString(value, textX, textY);
    }

    public void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width -10, height - 10, 25, 25);


    }

    public int getXforAlignRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }
}
