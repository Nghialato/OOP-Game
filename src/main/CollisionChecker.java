package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp = gp;

    }

    public void checkTile (Entity entity){
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.getDirection()) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
        }
    }
    public int checkObject(Entity entity, boolean player){
        int index = 999;

        for(int i = 0; i <gp.obj.length; i++){
            if(gp.obj[i] != null) {
                //get entity's splid area position
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;
                //get the object solid area position
                gp.obj[i].solidArea.x = gp.obj[i].getWorldX() + gp.obj[i].solidArea.y;
                gp.obj[i].solidArea.y = gp.obj[i].getWorldY() + gp.obj[i].solidArea.y;

                switch (entity.getDirection()){
                    case "up":
                        entity.getSolidArea().y -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(gp.obj[i].solidArea)) {
                            if(gp.obj[i].isCollision()){
                                entity.collisionOn = true;
                            }
                            if(player){
                                index = i;
                            }

                        }
                    case "down":
                        entity.getSolidArea().y += entity.getSpeed();
                        if (entity.getSolidArea().intersects(gp.obj[i].solidArea)) {
                            if(gp.obj[i].isCollision()){
                                entity.collisionOn = true;
                            }
                            if(player){
                                index = i;
                            }
                        }
                    case "left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(gp.obj[i].solidArea)) {
                            if(gp.obj[i].isCollision()){
                                entity.collisionOn = true;
                            }
                            if(player){
                                index = i;
                            }
                        }
                    case "right":
                        entity.getSolidArea().y += entity.getSpeed();
                        if (entity.getSolidArea().intersects(gp.obj[i].solidArea)) {
                            if(gp.obj[i].isCollision()){
                                entity.collisionOn = true;
                            }
                            if(player){
                                index = i;
                            }
                        }
                }
                entity.getSolidArea().x = entity.solidAreaDefaultX;
                entity.getSolidArea().y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;

            }
        }
        return index;
    }
}
