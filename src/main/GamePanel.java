package main;
import javax.swing.*;
import java.awt.*;
import java.security.Key;

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

    KeyHandler keyH = new KeyHandler();
    Thread gameThread; // Time in game

    // set Players default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){ //constructor
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.darkGray);
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
            while(gameThread != null){

                System.out.println("player X: "+playerX+" player Y: "+playerY);

                //1 update character position
                update();
                //2 draw the screen with updated info
                repaint(); //kaller på paintComponent under
            }
    }
    public void update(){
        if (keyH.upPressed){
            playerY -= playerSpeed;
        }
        else if (keyH.downPressed){
            playerY += playerSpeed;
        }
        else if (keyH.leftPressed){
            playerX-= playerSpeed;
        }
        else if (keyH.rightPressed){
            playerX+= playerSpeed;
        }
    }
    public void paintComponent(Graphics g){ //repaint

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g; // Graphics2D har flere functions enn den vanlige

        g2.setColor(Color.WHITE);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose(); // sier til systemer å slutte å bruke den når den er ferdig, bedre performance
    }
}
