package brickbreaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;

import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

  // initial states
  private boolean play = false;
  private int score = 0;

  // bricks to fit a 7x3 relative window spacing
  private int totalBricks = 21;

  // initial game time
  private Timer timer;
  private int delay = 8;

  // initial positions of platform and ball
  private int playerXPos = 310;

  private int ballXPos = 120;
  private int ballYPos = 350;
  private int ballYDir = -1;
  private int ballXDir = -2;

  public Gameplay() {
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    timer = new Timer(delay, this);
    timer.start();
  }

  public void paint(Graphics g) {
    // background
    g.setColor(Color.black);
    g.fillRect(1, 1, 692, 592);

    // scores
    g.setColor(Color.white);
    g.setFont(new Font("serif", Font.BOLD, 25));
    g.drawString(""+score, 675, 565);

    // borders
    g.setColor(Color.CYAN);
    g.fillRect(0, 0, 3, 591);
    g.fillRect(0, 0, 691, 3);
    g.fillRect(691, 0, 3, 591);

    // paddle
    g.setColor(Color.CYAN);
    g.fillRect(playerXPos, 550, 100, 8);

    // ball
    g.setColor(Color.CYAN);
    g.fillOval(ballXPos, ballYPos, 20, 20);

    g.dispose();
  }

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
    }
    repaint();
  }

  // not needed functions
  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

}
