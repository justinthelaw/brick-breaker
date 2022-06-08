package brickbreaker;

import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame obj = new JFrame();
        Gameplay gameplay = new Gameplay();

        obj.setBounds(10, 10, 710, 610);
        obj.setTitle("Brick Breaker");
        obj.setFocusable(true);
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);

    }
}
