package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //så man kan x-e ut av window
        window.setResizable(false);
        window.setTitle("Monke banna Rise [35 MYA]"); // 35 million years ago the monkeys rised to power

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null); //sentrerer den
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}