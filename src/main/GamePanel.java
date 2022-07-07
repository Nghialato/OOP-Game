package main;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements  Runnable{
    final int originalTileSize = 16;
    public final int scale = 3;

    public int tileSize = originalTileSize * scale;
    public int maxScreenCol = 15;
    public int maxScreenRow = 15;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    public SuperObject obj[] = new SuperObject[100];






    int FPS = 60 ;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this,keyH);


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame(){
        aSetter.setObject();
    }
    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run(){
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;


        while(gameThread != null){
            long currentTime = System.nanoTime();

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
        if(keyH.downPressed || keyH.upPressed || keyH.leftPressed || keyH.rightPressed) {
            player.update();
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        //tile
        tileM.draw(g2);

        //object
        for(int i = 0; i<obj.length;i++){
            if(obj[i] != null)
                obj[i].draw(g2, this);
        }
        //player
        player.draw(g2);

        g2.dispose();

    }
}
