package main;

import java.awt.*;


public class Menu{
    public int num, time;
    KeyHandler keyH;
    GamePanel gamePanel;
    

    public Menu(GamePanel gamePanel, KeyHandler keyH){
        this.keyH = keyH;
        this.gamePanel = gamePanel;
        num=50;
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

        text = "EXIT GAME";
        x=320;
        y=400;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if (num%2==0)
            g2.drawString(">", 320-50, 350);
        else g2.drawString(">", 320-50, 400);
    /*  if (num %2== 0){
            g2.drawString(">",225,300);
        } else g2.drawString(">",225,350);*/
    }
    public void update(){
        if (time > 10) {
            if (keyH.up){
                num++;
                System.out.println(num);
            }
            if (keyH.down){
                num--;
                System.out.println(num);
            }
            time = 0;
        }
        time ++;
    }
}