package main;

import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;


public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    BufferedImage heartImage;
    public UI(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);

            OBJ_Heart heart = new OBJ_Heart();
            heartImage = heart.get_image();

    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        /*g2.setFont( arial_40);
        g2.setColor(Color.yellow);
        g2.drawImage(heartImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x " + gp.player.getCurrent_health(), 74, 65);*/

        if(gp.gameState == gp.playState){

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

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80));
        String text = "Paused";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawOverScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80));
        String text = "Game Over";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text){
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
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
        textY += lineHeight;

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
        textY += lineHeight;


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
        int x = tailX - length;
        return x;
    }
}
