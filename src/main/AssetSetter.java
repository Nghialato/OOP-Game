package main;

import object.*;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp =gp;
    }
    public void setObject(){
        gp.obj[0] = new OBJ_Shoe();
        gp.obj[0].worldX = 9*gp.tileSize;
        gp.obj[0].worldY = 8*gp.tileSize;

        gp.obj[1] = new OBJ_Door();
        gp.obj[1].worldX = 18*gp.tileSize;
        gp.obj[1].worldY = gp.tileSize;

        gp.obj[2] = new OBJ_Chest();
        gp.obj[2].worldX = 8*gp.tileSize;
        gp.obj[2].worldY = 4*gp.tileSize;

        gp.obj[3] = new OBJ_Heart();
        gp.obj[3].worldX = 5*gp.tileSize;
        gp.obj[3].worldY = 5*gp.tileSize;

        gp.obj[4] = new OBJ_Door();
        gp.obj[4].worldX = gp.tileSize;
        gp.obj[4].worldY = 14*gp.tileSize;

       /* gp.obj[5] = new OBJ_Key();
        gp.obj[5].worldX = 5*gp.tileSize;
        gp.obj[5].worldY = 5*gp.tileSize;

*/
        gp.obj[6] = new OBJ_Bomb();
        gp.obj[6].worldX = 14*gp.tileSize;
        gp.obj[6].worldY = 10*gp.tileSize;


        gp.obj[7] = new OBJ_Heart();
        gp.obj[7].worldX = 12*gp.tileSize;
        gp.obj[7].worldY = 14*gp.tileSize;

        gp.obj[8] = new OBJ_Shoe();
        gp.obj[8].worldX = 16*gp.tileSize;
        gp.obj[8].worldY = 8*gp.tileSize;

        gp.obj[9] = new OBJ_Bomb();
        gp.obj[9].worldX = 14*gp.tileSize;
        gp.obj[9].worldY = 2*gp.tileSize;

    }
}
