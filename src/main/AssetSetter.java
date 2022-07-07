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
        gp.obj[0].x = 200;
        gp.obj[0].y = 200;

        gp.obj[1] = new OBJ_Door();
        gp.obj[1].x = 620;
        gp.obj[1].y = 50;

        gp.obj[2] = new OBJ_Chest();
        gp.obj[2].x = 150;
        gp.obj[2].y = 150;

        gp.obj[3] = new OBJ_Heart();
        gp.obj[3].x = 250;
        gp.obj[3].y = 250;

        gp.obj[4] = new OBJ_Door();
        gp.obj[4].x = 50;
        gp.obj[4].y = 620;
    }
}
