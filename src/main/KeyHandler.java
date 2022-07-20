package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    GamePanel gp;

    public boolean upPressed, downPressed,enterPressed2=false, leftPressed, rightPressed, spacePressed, rPressed=false, tabPressed, up, down, enterPressed,escapePressed;
    public int numP=0;
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    public void keyTyped(KeyEvent e) {
    }


    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_ENTER && enterPressed == true ){
            enterPressed2 = true;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        if(code == KeyEvent.VK_R){
            rPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE){
            escapePressed = true;
        }
        if (enterPressed){
            if(code == KeyEvent.VK_W){
                upPressed = true;
            }
            if(code == KeyEvent.VK_S){
                downPressed = true;
            }
            if(code == KeyEvent.VK_A){
                leftPressed = true;
            }
            if(code == KeyEvent.VK_D){
                rightPressed = true;
            }
            if(code == KeyEvent.VK_SPACE){
                spacePressed = true;
            }
        }
        if(code == KeyEvent.VK_UP){
            up = true;
        }
        if(code == KeyEvent.VK_DOWN){
            down = true;
        }
        if(code == KeyEvent.VK_V){
            gp.gameState = gp.playerStatus;
        }
        if(code == KeyEvent.VK_P){
            numP++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
        if(code == KeyEvent.VK_V){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_UP){
            up = false;
        }
        if(code == KeyEvent.VK_DOWN){
            down = false;
        }
        if(code == KeyEvent.VK_R){
            rPressed = false;
        }
        if(code == KeyEvent.VK_ENTER && enterPressed == true ){
            enterPressed2 = false;
        }
        if(code == KeyEvent.VK_ESCAPE){
            escapePressed = false;
        }
     /*  *//* else if(gp.gameState == gp.playerStatus){
            if(code == KeyEvent.VK_C){
                gp.gameState = gp.playerStatus;
                }
*//*
    }*/

    }
}
