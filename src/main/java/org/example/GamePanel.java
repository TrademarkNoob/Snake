package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

static final int SCREEN_WIDTH = 600;
static final int SCREEN_HEIGHT = 600;
static final int UNIT_SIZE = 25;
static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
static final int DELAY = 75;
final int[] x = new int[GAME_UNITS];
final int[] y = new int[GAME_UNITS];
int bodyParts = 6;
int applesEaten;
int appleX;
int appleY;
char direction = 'D';
boolean playing = false;
Timer timer;
Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }
    public void startGame() {
        newApple();
        playing = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g) {
        if(playing) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);

            }
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Bahnschrift", Font.BOLD, 20));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 30, (SCREEN_HEIGHT / 30));
            } else {
            gameOver(g);
        }
    }
    public void newApple() {
        appleX = random.nextInt((SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }
    public void move() {
        for(int i = bodyParts; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction) {
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
        }

    }
    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }
    public void checkCollision() {
        //tjekker efter hovedet kollidere med kroppen
        for(int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i] && (y[0] == y[i]))) {
                playing = false;

                break;
            }
            //tjekker om hovedet rør venstre side af skærmen
        } if(x[0] < 0) {
            playing = false;

            //tjekker om hovedet rør højre side af skærmen
        } if(x[0] > SCREEN_WIDTH) {
            playing = false;

            //tjekker om hovedet rør toppen af skærmen
        } if(y[0] < 0) {
            playing = false;

            //tjekker om hovedet rør bunden af skærmen
        } if(y[0] > SCREEN_HEIGHT) {
            playing = false;

        } if(!playing) {
            timer.stop();

        }


    }
    public void gameOver(Graphics g) {
        //Score
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Bahnschrift", Font.BOLD, 20));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten)) / 3, (SCREEN_HEIGHT / 2));

        //Game over
        g.setColor(Color.RED);
        g.setFont(new Font("Bahnschrift", Font.BOLD, 50));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Game Over!", (SCREEN_WIDTH - metrics1.stringWidth("Game Over!")) / 2, (int) (SCREEN_HEIGHT / 2.5f));

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(playing) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> {
                    if (direction != 'R') {
                        direction = 'L';
                    }
                }
                case KeyEvent.VK_D -> {
                    if (direction != 'L') {
                        direction = 'R';
                    }
                }
                case KeyEvent.VK_W -> {
                    if (direction != 'D') {
                        direction = 'U';
                    }
                }
                case KeyEvent.VK_S -> {
                    if (direction != 'U') {
                        direction = 'D';
                    }
                }
            }

        }
    }

}
