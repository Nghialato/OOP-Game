package main;

import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Shoe;

public class AssetSetter {

    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp =gp;
    }

    public void setObject(){
        gp.obj[0] = new OBJ_Shoe();
        gp.obj[0].worldX = gp.tileSize;
        gp.obj[0].worldY = gp.tileSize;

        gp.obj[1] = new OBJ_Door();
        gp.obj[1].worldX = 2*gp.tileSize;
        gp.obj[1].worldY = 2*gp.tileSize;

        gp.obj[2] = new OBJ_Chest();
        gp.obj[2].worldX = 3*gp.tileSize;
        gp.obj[2].worldY = 3*gp.tileSize;

        gp.obj[3] = new OBJ_Heart();
        gp.obj[3].worldX = 4*gp.tileSize;
        gp.obj[3].worldY = 4*gp.tileSize;

        gp.obj[4] = new OBJ_Door();
        gp.obj[4].worldX = 5*gp.tileSize;
        gp.obj[4].worldY = 5*gp.tileSize;
    }
}
