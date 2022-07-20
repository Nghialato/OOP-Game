package main;

import java.awt.*;


public class Menu{
    public int nums, num, time;
    boolean start = true;
    KeyHandler keyH;
    GamePanel gamePanel;
    

    public Menu(GamePanel gamePanel, KeyHandler keyH){
        this.keyH = keyH;
        this.gamePanel = gamePanel;
        num=0;
        nums=1;
    }
/* BufferedImage name;
    public void getPlayerImage(){
        try {
            name = ImageIO.read(getClass().getResourceAsStream("/menu/name.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public void draw(Graphics2D g2) {
        g2.setFont(new Font("Pixeboy", Font.PLAIN, 100));
        g2.setColor(new Color(0,150,200));
        g2.fillRect(0, 0, 1800,1800);
        String text = "BACK KHOA BOMB";
        int x=180;
        int y=180;
        g2.setColor(Color.black);
        g2.drawString(text, x, y);
        g2.setColor(Color.white);
        g2.drawString(text, x+5, y-5);
        g2.setFont(new Font("Pixeboy", Font.PLAIN, 70));
        text = "START GAME";
        x=320;
        y=350;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        text = "SOUND";
        x=320;
        y=400;
        g2.drawString(text, x, y);
        text = "HELP";
        x=320;
        y=450;
        g2.drawString(text, x, y);
        text = "EXIT GAME";
        x=320;
        y=500;
        g2.drawString(text, x, y);
        if (num==0)
            g2.drawString(">", 320-50, 350);
        else if (num==1) g2.drawString(">", 320-50, 400);
            else if (num==2) g2.drawString(">", 320-50, 450);
                else if (num==3) g2.drawString(">", 320-50, 500);
    }
    public void drawContinue(Graphics2D g2) {

        g2.setFont(new Font("Pixeboy", Font.PLAIN, 100));
        g2.setColor(new Color(0,150,200));
        g2.fillRect(0, 0, 1800,1800);
        String text = "BACK KHOA BOMB";
        int x=180;
        int y=180;
        g2.setColor(Color.black);
        g2.drawString(text, x, y);
        g2.setColor(Color.white);
        g2.drawString(text, x+5, y-5);
        g2.setFont(new Font("Pixeboy", Font.PLAIN, 70));
        text = "CONTINUE";
        x=320;
        y=350;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        text = "SOUND";
        x=320;
        y=400;
        g2.drawString(text, x, y);
        text = "HELP";
        x=320;
        y=450;
        g2.drawString(text, x, y);
        text = "EXIT GAME";
        x=320;
        y=500;
        g2.drawString(text, x, y);
        if (num==0)
            g2.drawString(">", 320-50, 350);
        else if (num==1) g2.drawString(">", 320-50, 400);
            else if (num==2) g2.drawString(">", 320-50, 450);
                else if (num==3) g2.drawString(">", 320-50, 500);
    }
    public void drawSound(Graphics2D g2) {
        g2.setFont(new Font("Pixeboy", Font.PLAIN, 100));
        g2.setColor(new Color(0,150,200));
        g2.fillRect(0, 0, 1800,1800);
        String text = "SOUND SETTING";
        int x=180;
        int y=180;
        g2.setColor(Color.white);
        g2.drawString(text, x+5, y-5);
        g2.setFont(new Font("Pixeboy", Font.PLAIN, 70));
        text = "MUTE";
        x=320;
        y=350;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        text = "UNMUTE";
        x=320;
        y=400;
        g2.drawString(text, x, y);
        if (keyH.enterPressed && num==1 && nums==0)
            g2.drawString(">", 320-50, 350);
        else if (keyH.enterPressed && num==1 && nums==1) g2.drawString(">", 320-50, 400);
    }
    public void drawHelp(Graphics2D g2) {
        g2.setFont(new Font("Pixeboy", Font.PLAIN, 100));
        g2.setColor(new Color(0,150,200));
        g2.fillRect(0, 0, 1800,1800);
        String text = "GUIDE";
        int x=370;
        int y=180;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        g2.setFont(new Font("Pixeboy", Font.PLAIN, 70));
        text = "W, A, S, D : di chuyen";
        x=250;
        y=350;
        g2.drawString(text, x, y);
        text = "SPACE : dat Bomb";
        x=250;
        y=400;
        g2.drawString(text, x, y);
        text = "ENTER : chon";
        x=250;
        y=450;
        g2.drawString(text, x, y);
        text = "ESC : thoat";
        x=250;
        y=500;
        g2.drawString(text, x, y);
    }
    public void update(){
        if (time > 8) {
            if (keyH.up){
                if (!keyH.enterPressed){
                num--;
                if (num<0) num = 3;}
                if (keyH.enterPressed && num==1) {
                    nums--;
                    if (nums<0) nums = 1;}
                System.out.println(num);
            }
            if (keyH.down){
                if (!keyH.enterPressed){
                    num++;
                    if (num>3) num = 0;}
                    if (keyH.enterPressed && num==1) {
                        nums++;
                        if (nums>1) nums = 0;}
                System.out.println(num);
            }
            time = 0;
        }
        time ++;
    }
}