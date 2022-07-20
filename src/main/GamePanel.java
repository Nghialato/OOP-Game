package main;
import entity.Boss;
import entity.E_Randomly;
import entity.Player;
import tile.Bomb;
import tile.SuperObject;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;

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
    public int gameState=5;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameOverState = 3;
    public final int playerStatus = 4;
    public final int menuState = 5;
    public boolean gameStart = false;

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

        obj[2] = new SuperObject(7, 5, "chest", this);

        obj[3] = new SuperObject(3, 7, "heart", this);

        obj[4] = new SuperObject(1, 14, "door", this);

        obj[5] = new SuperObject(1, 13, "bomb", this);

        obj[6] = new SuperObject(12, 1, "bomb_1", this);

        obj[7] = new SuperObject(7, 11, "heart", this);

        obj[8] = new SuperObject(8, 7, "bomb", this);

        obj[9] = new SuperObject(8, 9, "bomb_1", this);

        obj[10] = new SuperObject(17, 4, "chest", this);
    }
    // public void soundGame(){
    //     if (menu.nums==1){
    //         sound.setFile(3);
    //         sound.play();
    //         sound.loop();
    //         sound.setFile(2);     
    //         sound.play();
    //         sound.loop();       
    //         sound.setFile(3);
    //         sound.play();
    //         sound.close();
    //         sound.close();
    //         sound.stop();
    //     }
    // }

    public void resetGame() {
            player.setDefaultValues();
            for (int i=0; i<7; i++) {e_Randomly[i].setDefaultValues();}
            boss.setDefaultValues();
            Bomb.bomb_range = 1;


            if (obj[0]==null) obj[0] = new SuperObject(5, 1, "shoe", this);
            else obj[0].setVisuable(false);

            if (obj[1]==null) obj[1] = new SuperObject(18, 1, "door", this);
            else obj[1].setVisuable(false);

            if (obj[2]==null) obj[2] = new SuperObject(7, 5, "chest", this);
            else obj[2].setVisuable(false);

            if (obj[3]==null) obj[3] = new SuperObject(3, 7, "heart", this);
            else obj[3].setVisuable(false);

            if (obj[4]==null) obj[4] = new SuperObject(1, 14, "door", this);
            else obj[4].setVisuable(false);

            if (obj[5]==null) obj[5] = new SuperObject(1, 13, "bomb", this);
            else obj[5].setVisuable(false);

            if (obj[6]==null) obj[6] = new SuperObject(12, 1, "bomb_1", this);
            else obj[6].setVisuable(false);

            if (obj[7]==null) obj[7] = new SuperObject(7, 11, "heart", this);
            else obj[7].setVisuable(false);

            if (obj[8]==null) obj[8] = new SuperObject(8, 7, "bomb", this);
            else obj[8].setVisuable(false);

            if (obj[9]==null) obj[9] = new SuperObject(8, 9, "bomb_1", this);
            else obj[9].setVisuable(false);

            if (obj[10]==null) obj[10] = new SuperObject(17, 4, "chest", this);
            else obj[10].setVisuable(false);

            tileM.loadmap("/maps/map02.txt");
            
            if (menu.nums==1) playMusic(3);
    }
    
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
        playMusic(3);
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
        System.out.println(gameState);
        if(keyH.numP%2==1){
            gameState = pauseState;
        }
        if(gameState == playState){
            menu.update();
            player.update();
            tileM.update();
        for(int i=0; i<7; i++) if (e_Randomly[i].getCurrent_health() != 0) e_Randomly[i].update();
        if (Boss.active) boss.update();
        }
        if(gameState == menuState){            
            menu.update();
        }
        if(gameState == gameOverState){
            if (keyH.rPressed){
                stopMusic();
                resetGame();
                menu.start=true;
                menu.num=0;
                keyH.numP=0;
                gameStart=false;
                keyH.enterPressed=false;
                keyH.enterPressed2=false;
                gameState = menuState;
            }
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        if (gameState==gameOverState){
            stopMusic();
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
            boss.draw(g2);
            for(int i=0; i<7; i++) if (e_Randomly[i].getCurrent_health() != 0) e_Randomly[i].draw(g2);
            ui.draw(g2);
            player.draw(g2);
            ui.drawOverScreen();
        } else
            if (keyH.numP%2==1){
                if (menu.nums==1){
                    sound.play();
                }
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
                for(int i=0; i<7; i++) if (e_Randomly[i].getCurrent_health() != 0) e_Randomly[i].draw(g2);
                ui.draw(g2);
                boss.draw(g2);
                player.draw(g2);
                ui.drawPauseScreen();
            } 
            else {
                    if (keyH.enterPressed && menu.num==0 && keyH.numP%2==0){
                        gameState=playState;
                        gameStart=true;
                        if (menu.nums==1){
                            sound.play();
                        }
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
                        for(int i=0; i<7; i++) if (e_Randomly[i].getCurrent_health() != 0) e_Randomly[i].draw(g2);
                        ui.draw(g2);
                        boss.draw(g2);
                        player.draw(g2);
                        if(keyH.escapePressed) {
                            menu.start=false;
                            gameState=menuState;
                            menu.num=0;
                            menu.nums=0;
                            keyH.enterPressed=false;
                            keyH.enterPressed2=false;
                        }
                    } else if (keyH.enterPressed && menu.num==1 && keyH.numP%2==0) {
                            menu.drawSound(g2); 
                            if(keyH.enterPressed2 && menu.nums ==0){
                               stopMusic();
                            }
                            if(keyH.enterPressed2 && menu.nums ==1){
                                sound.play();
                            sound.loop();
                            }
                            if(keyH.escapePressed) {
                                menu.num=0;
                                menu.nums=0;
                                keyH.enterPressed=false;
                                keyH.enterPressed2=false;
                            }
                        }
                        else if (!gameStart && keyH.numP%2==0 && menu.start) menu.draw(g2);
                            else if (keyH.numP%2==0 && !menu.start) menu.drawContinue(g2);

                if (keyH.enterPressed && menu.num==2) {
                    menu.drawHelp(g2);
                    if(keyH.escapePressed) {
                        menu.num=0;
                        menu.nums=0;
                        keyH.enterPressed=false;
                        keyH.enterPressed2=false;
                    }
                }
                if (keyH.enterPressed && menu.num==3) System.exit(0);}
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
        sound.reset();
    }
}
