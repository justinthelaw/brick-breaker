package brickbreaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.Timer;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

  // initial states
  private boolean play = false;
  private int score = 0;

  // bricks to fit a 7x3 relative window spacing
  public int row = 3;
  public int col = 7;
  public int totalBricks = row * col;
  public MapGenerator brickLayout;

  // initial game time
  private Timer timer;
  private int delay = 8;

  // initial positions of platform and ball
  private int playerXPos = 310;

  private int ballXPos = 120;
  private int ballYPos = 350;
  private int ballYDir = -2;
  private int ballXDir = -3;

  public Gameplay() {
    addKeyListener(this);
    setFocusable(true);
    requestFocus();
    setFocusTraversalKeysEnabled(false);
    timer = new Timer(delay, this);
    timer.start();

    // try and catch block for map generation errors
    try {
      brickLayout = new MapGenerator(row, col);
    } catch (Exception e) {
      System.out.print("RuntimeException: ");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public void paint(Graphics g) {
    // background
    g.setColor(Color.BLACK);
    g.fillRect(1, 1, 692, 592);

    // scores
    g.setColor(Color.WHITE);
    g.setFont(new Font("serif", Font.BOLD, 25));
    g.drawString("" + score, 650, 30);

    // GAME OVER message
    if (ballYPos > 570) {
      play = false;
      ballXDir = 0;
      ballYDir = 0;

      g.setColor(Color.RED);
      g.setFont(new Font("serif", Font.BOLD, 30));
      g.drawString("GAME OVER! Final Score: " + score, 150, 300);
      g.setFont(new Font("serif", Font.BOLD, 20));
      g.drawString("Press Enter to Play Again!", 230, 350);
    }

    // YOU WON message
    if (totalBricks == 0) {
      play = false;

      g.setColor(Color.RED);
      g.setFont(new Font("serif", Font.BOLD, 30));
      g.drawString("YOU WON! Final Score: " + score, 190, 300);
      g.setFont(new Font("serif", Font.BOLD, 20));
      g.drawString("Press Enter to Play Again!", 230, 350);
    }

    // borders
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, 3, 591);
    g.fillRect(0, 0, 691, 3);
    g.fillRect(691, 0, 3, 591);

    // paddle
    g.setColor(Color.WHITE);
    g.fillRect(playerXPos, 550, 100, 8);

    // draw brick map
    brickLayout.draw((Graphics2D) g);

    // ball
    g.setColor(Color.WHITE);
    g.fillOval(ballXPos, ballYPos, 20, 20);

    g.dispose();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    timer.start();
    if (play) {
      ballXPos += ballXDir;
      ballYPos += ballYDir;
      if (ballXPos < 0) {
        ballXDir = -ballXDir;
      }
      if (ballYPos < 0) {
        ballYDir = -ballYDir;
      }
      if (ballXPos > 670) {
        ballXDir = -ballXDir;
      }
      if ((ballYPos >= 540 && ballYPos <= 550) && (ballXPos <= playerXPos + 100 && ballXPos >= playerXPos - 100)) {
        // can also use the Rectangle intersects function from java.awt.rectangle
        ballYDir = -ballYDir;
      }

      // where brickLayout is the instantation of MapGenerator,
      // and map is an internally created variable in MapGenerator
      A: for (int i = 0; i < brickLayout.map.length; i++) {
        for (int j = 0; j < brickLayout.map[0].length; j++) {
          if (brickLayout.map[i][j] > 0) {
            // generate the outer-dimensions of each brick
            int brickXPos = j * brickLayout.brickWidth + 80;
            int brickYPos = i * brickLayout.brickHeight + 50;
            int brickWidth = brickLayout.brickWidth;
            int brickHeight = brickLayout.brickHeight;

            // use brick and ball dimensions and positions to generate collision borders
            Rectangle brickRect = new Rectangle(brickXPos, brickYPos, brickWidth, brickHeight);
            Rectangle ballRect = new Rectangle(ballXPos, ballYPos, 20, 20);

            if (ballRect.intersects(brickRect)) {
              // delete brick in 2D matrix and subtract bricks from score
              brickLayout.setBrickValue(0, i, j);
              totalBricks -= 1;
              score += 5;

              // deflect ball after intersects
              if (ballXPos + 19 <= brickRect.x || ballXPos + 1 >= brickRect.x + brickRect.width) {
                ballXDir = -ballXDir;
              } else {
                ballYDir = -ballYDir;
              }
              break A; // takes it out of outer for-loop
            }
          }
        }
      }
    }
    repaint();
  }

  @Override
  public void keyReleased(KeyEvent e) {
  } // not needed

  @Override
  public void keyTyped(KeyEvent e) {
  } // not needed

  public void moveRight() {
    play = true;
    playerXPos += 20;
  }

  public void moveLeft() {
    play = true;
    playerXPos -= 20;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT || key == 39) {
      if (playerXPos >= 600) {
        playerXPos = 600;
      } else {
        moveRight();
      }
    }
    if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT || key == 37) {
      if (playerXPos < 10) {
        playerXPos = 10;
      } else {
        moveLeft();
      }
    }

    // reset game
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (!play) {
        play = true;
        ballYPos = 350;
        ballXPos = 120;
        ballXDir = -2;
        ballYDir = -3;
        playerXPos = 310;
        score = 0;
        totalBricks = row * col;
        brickLayout = new MapGenerator(row, col);

        repaint();
      }
    }

  }
}
