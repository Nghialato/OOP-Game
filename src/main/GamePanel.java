package main;
import entity.Boss;
import entity.E_Randomly;
import entity.Player;
import tile.Bomb;
import tile.SuperObject;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;
import java.util.Arrays;

public class GamePanel extends JPanel implements  Runnable{
    final int originalTileSize = 16;
    public final int scale = 3;

    public int tileSize = originalTileSize * scale;
    public int maxScreenCol = 20;
    public int maxScreenRow = 16;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    KeyHandler keyH = new KeyHandler(this);
    Menu menu = new Menu(this, keyH);
    /*Thread gameThread;*/
    public CollisionChecker cChecker = new CollisionChecker(this);
    public UI ui = new UI(this);

    public Player player = new Player(this,keyH);
    public SuperObject[] obj = new SuperObject[100];
    public E_Randomly[] e_Randomly = new E_Randomly[7];
    public Boss boss = new Boss(this, player);
    //game state
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameOverState = 3;
    public final int playerStatus = 4;


    int FPS = 60 ;
    TileManager tileM = new TileManager(this, player);
    Sound sound = new Sound();
    Thread gameThread;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        for (int i=0; i<7; i++) e_Randomly[i] = new E_Randomly(this, player, 2*i+1);
        obj[0] = new SuperObject(5, 1, "shoe", this);

        obj[1] = new SuperObject(18, 1, "door", this);

        obj[2] = new SuperObject(8, 4, "chest", this);

        obj[3] = new SuperObject(5, 5, "heart", this);

        obj[4] = new SuperObject(1, 14, "door", this);

        obj[6] = new SuperObject(14, 10, "bomb", this);

        obj[7] = new SuperObject(12, 10, "bomb_1", this);
    }

    public void setUpGame() {
        gameState=playState;

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
        playMusic(3);

    }
    public void gameOver(){
        if (keyH.rPressed){
            setUpGame();
            startGameThread();
        }
    }
    public void run(){
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;


        while(gameThread != null){

            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime <0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime +=drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void update() {
        if(gameState == playState){
            player.update();
            tileM.update();
            menu.update();
        for(int i=0; i<7; i++) if (e_Randomly[i].getCurrent_health() != 0) e_Randomly[i].update();
        if (Boss.active) boss.update();
        }
        if(gameState == pauseState){
        }
        if(gameState == gameOverState){
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if (keyH.enterPressed && menu.num%2==0){
            tileM.draw(g2);
            for(SuperObject superObject : obj){
                if(superObject != null){

                    if (tileM.mapTileNum[superObject.getWorldX()/tileSize][superObject.getWorldY()/tileSize] == 0 || tileM.mapTileNum[superObject.getWorldX()/tileSize][superObject.getWorldY()/tileSize] == 9) {
                        superObject.setVisuable(true);
                    }
                    if (superObject.isVisuable()) {
                        superObject.draw(g2, this);
                    }
                }
            }
            ui.draw(g2);
            boss.draw(g2);
            player.draw(g2);
            for(int i=0; i<7; i++) if (e_Randomly[i].getCurrent_health() != 0) e_Randomly[i].draw(g2);} else menu.draw(g2);
            if (keyH.enterPressed && menu.num%2==1) System.exit(0);
    }
    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic(){
        sound.stop();
    }
    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
}
