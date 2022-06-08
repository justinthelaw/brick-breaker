package brickbreaker;

import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        JFrame gameFrame = new JFrame();
        Gameplay gameplay = new Gameplay();

        gameFrame.setBounds(10, 10, 710, 610);
        gameFrame.setTitle("Brick Breaker");
        gameFrame.setResizable(false);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.addKeyListener(gameplay);
        gameFrame.add(gameplay);
    }
}
