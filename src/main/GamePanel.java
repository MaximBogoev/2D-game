package main;
import entities.Player;
import tiles.TileManager;

import javax.swing.*;
import java.awt.*;

// Game Panel blir subclass til JPanel
public class GamePanel extends JPanel implements Runnable{ //runnable så man kan bruke Thread
    //Screen Settings:
    final int originalTileSize = 16; // alt i spillet skal være 16x16, retro
    final int scale = 3; // 16x16 på 1920x1080 skjerm er smått så scale 16x3
    public final int tileSize = originalTileSize * scale;

    // Tile Grid
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12; //4*3 ratio
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; // Time in game
    public Player player = new Player(this,keyH);

    public GamePanel(){ //constructor
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //bedre rendering
        this.addKeyListener(keyH);
        this.setFocusable(true); //focused to receive key inputs
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() { //game loop (core of the game)

        double drawInterval = 1000000000 / FPS; // 60 FPS = 0.16666... sec
        double nextDrawTime = System.nanoTime() + drawInterval;

            while(gameThread != null){

                //1 update character position
                update();
                //2 draw the screen with updated info
                repaint(); //kaller på paintComponent under

                try {
                    double remainingTime = nextDrawTime - System.nanoTime();
                    remainingTime = remainingTime / 1000000; // nano til milisekunder
                    if (remainingTime<0) remainingTime = 0;

                    Thread.sleep((long) remainingTime); //dette er ikke busy wait så ignorer warningen

                    nextDrawTime += drawInterval;

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
    }
    public void update(){
        player.update();
    }
    public void paintComponent(Graphics g){ //repaint

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g; // Graphics2D har flere functions enn den vanlige

        tileM.draw(g2); // tegne tile først så player kommer opp på og ikke under tiles

        player.draw(g2);

        g2.dispose(); // sier til systemer å slutte å bruke den når den er ferdig, bedre performance
    }
}
