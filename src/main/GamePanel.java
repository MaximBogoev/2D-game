package main;
import javax.swing.*;
import java.awt.*;

// Game Panel blir subclass til JPanel
public class GamePanel extends JPanel implements Runnable{ //runnable så man kan bruke Thread
    //Screen settings:
    final int originalTileSize = 16; // alt i spillet skal være 16x16, retro
    final int scale = 3; // 16x16 på 1920x1080 skjerm er smått så scale 16x3

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12; //4*3 ratio

    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    public GamePanel(){ //constructor
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true); //bedre rendering
    }

    // Time in game
    Thread gameThread;

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() { //game loop (core of the game)

    }
}
