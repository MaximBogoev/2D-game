package tiles;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Objects;
import java.io.BufferedReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[10]; // antall tiles
        mapTileNum = new int [gp.maxWorldCol] [gp.maxWorldRow];
        getTileImage();
        loadMap("maps/map01.txt");
    }
    public void getTileImage(){
        try{

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/water.png")));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/brick.png")));
            tile[2].collision = true;
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); //format for å lese tekst, ligner på bufferedImage

            int col = 0;
            int row = 0;
            //scan map.txt linje etter linje
            while (col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();

                while (col < gp.maxWorldCol){
                    String[] numberString = line.split(" "); //splitter der den ser mellomrom, som i map txt imellom hver tile nummer
                    int numbers  = Integer.parseInt(numberString[col]); // gjør om lest String til int

                    mapTileNum[col] [row] = numbers;
                    col++;
                }
                if (col == gp.maxWorldCol){
                    row++;
                    col = 0;
                }
            }
            br.close(); //avslutt for performance
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];
            // worldX,Y er hvor tile er på mappet, screenX,Y er hvor det skal bli tegnet på skjermen
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
// if statement som sjekker at tile er innenfor player view, hvis ja, kun da draw it.
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
               worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
               worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
               worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize,null);
            }// vi går fra å tegne 50*50 (map size) som er 2500 tiles hver frame,
            // ned til 16*12 det innenfor skjermen, 192, mye bedre for performance
            worldCol++;
            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
