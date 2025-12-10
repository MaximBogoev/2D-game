package main;

import entities.Entity;

public class CollisionChecker {

    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }
    public void checkTile(Entity entity){
        //alle 4 sidene av hitboxen
        int leftX = entity.worldX + entity.hitbox.x;
        int rightX = entity.worldX + entity.hitbox.x + entity.hitbox.width;
        int topY = entity.worldY + entity.hitbox.y;
        int bottomY = entity.worldY + entity.hitbox.y + entity.hitbox.height;

        //hvilken tile de 4 er på
        int leftCol = leftX / gp.tileSize;
        int rightCol = rightX / gp.tileSize;
        int topRow = topY / gp.tileSize;
        int bottomRow = bottomY / gp.tileSize;

        int tileNum1, tileNum2; //hver retning krever bare sjekk på 2 corners, f.eks up = topleft and right

        switch (entity.direction){
            case "up":
                topRow = (topY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[leftCol][topRow];
                tileNum2 = gp.tileM.mapTileNum[rightCol][topRow];
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.colliding = true;
                }
                break;
            case "down":
                bottomRow = (bottomY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[leftCol][bottomRow];
                tileNum2 = gp.tileM.mapTileNum[rightCol][bottomRow];
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.colliding = true;
                }
                break;
            case "left":
                leftCol = (leftX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[leftCol][topRow];
                tileNum2 = gp.tileM.mapTileNum[leftCol][bottomRow];
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.colliding = true;
                }
                break;
            case "right":
                rightCol = (rightX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[rightCol][topRow];
                tileNum2 = gp.tileM.mapTileNum[rightCol][bottomRow];
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.colliding = true;
                }
                break;
        }
    }
}
