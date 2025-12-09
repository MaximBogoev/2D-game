package tiles;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Objects;
import java.io.BufferedReader;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNum;

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[10]; // antall tiles
        mapTileNum = new int [gp.maxScreenCol] [gp.maxScreenRow];
        getTileImage();
        loadMap("maps/map01.txt");
    }
    public void getTileImage(){
        try{

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/water.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/brick.png")));
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
            while (col < gp.maxScreenCol && row < gp.maxScreenRow){
                String line = br.readLine();

                while (col < gp.maxScreenCol){
                    String[] numberString = line.split(" "); //splitter der den ser mellomrom, som i map txt imellom hver tile nummer
                    int numbers  = Integer.parseInt(numberString[col]); // gjør om lest String til int

                    mapTileNum[col] [row] = numbers;
                    col++;
                }
                if (col == gp.maxScreenCol){
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

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){

            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize,null);
            col++;
            x += gp.tileSize;
            if(col == gp.maxScreenCol){
                row++;
                y += gp.tileSize;
                col = 0;
                x = 0;
            }
        }
    }
}
