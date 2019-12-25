package Pong;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.*;

public class Pong implements ActionListener, KeyListener {
    public static Pong pong = new Pong();
    public final int WIDTH = 700, HEIGHT = 700;

    public final Random rand = new Random();

    public int speed = 2;
    public int deltaX = (-3 + rand.nextInt(6)) * speed;
    public int deltaY = (-3 + rand.nextInt(6)) * speed;

    //game logistics
    public boolean w = false;
    public boolean s = false;
    public boolean up = false;
    public boolean down = false;
    public boolean started = false;
    public boolean paused = false;

    //bot
    public boolean bot = false;
    public int level;

    //scores
    public int score1; //blue
    public int score2; //red

    //renderer
    public Renderer renderer = new Renderer();

    //frame
    public int frame = 0;
    public int oldFrame = frame;


    //rectangles
    public Rectangle ball = new Rectangle((WIDTH/2)-6, (HEIGHT/2)-6, 12, 12);
    public Rectangle player1 = new Rectangle((WIDTH*19)/20, (HEIGHT/2) - 75, 12, 100);
    public Rectangle player2 = new Rectangle(WIDTH/40, (HEIGHT/2) - 75, 12, 100);

    public Pong() {
        JFrame frame = new JFrame();
        Timer timer = new Timer(1000/60, this);

        while (deltaX == 0) {
            deltaX = (-3 + rand.nextInt(6)) * speed;
        }
        while (deltaY == 0) {
            deltaY = (-3 + rand.nextInt(6)) * speed;
        }

        frame.add(renderer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT+ 20);
        frame.setFocusable(true);
        frame.setTitle("Pong");
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setBackground(Color.black);
        frame.addKeyListener(this);

        timer.start();
    }

    public static void main(String[] args) {

    }

    public void player1Move(boolean up) {
        if (up) {
            player1.y -= speed * 6;
        } else {
            player1.y += speed * 6;
        }
    }

    public void player2Move(boolean up) {
        if (up) {
            player2.y -= speed * 6;
        } else {
            player2.y += speed * 6;
        }
    }

    public void playerUpdate() {
        if (w) {
            player2Move(true);
        }
        if (s) {
            player2Move(false);
        }
        if (up) {
            player1Move(true);
        }
        if (down) {
            player1Move(false);
        }

        if (player1.y >= HEIGHT - player1.height) {
            player1.y = HEIGHT - player1.height;
        }
        if (player1.y <= 0) {
            player1.y = 0;
        }
        if (player2.y >= HEIGHT - player2.height) {
            player2.y = HEIGHT - player2.height;
        }
        if (player2.y <= 0) {
            player2.y = 0;
        }

        if (player1.intersects(ball)) {
            if (oldFrame <= frame - 5) {
                if (ball.x + ball.width <= player1.x + 6) {
                    deltaX *= -1;
                } else {
                    deltaX = (-6 + rand.nextInt(3)) * speed;
                    deltaY = (-6 + rand.nextInt(3)) * speed;

                    while (deltaX == 0) {
                        deltaX = (-6 + rand.nextInt(3)) * speed;
                    }
                    while (deltaY == 0) {
                        deltaY = (-6 + rand.nextInt(3)) * speed;
                    }
                }
                oldFrame = frame;
            }
        }
        if (player2.intersects(ball)) {
            if (oldFrame <= frame - 5) {
                if (ball.x + 6 >= player2.x + player2.width) {
                    deltaX *= -1;
                } else {
                    deltaX = (3 - rand.nextInt(3)) * speed;
                    deltaY = (3 - rand.nextInt(3)) * speed;

                    while (deltaX == 0) {
                        deltaX = (3 - rand.nextInt(3)) * speed;
                    }
                    while (deltaY == 0) {
                        deltaY = (3 - rand.nextInt(3)) * speed;
                    }
                }
                oldFrame = frame;
            }
        }
    }

    public void ballUpdate() {
        if (ball.y + ball.width >= HEIGHT || ball.y <= 0) {
            deltaY *= -1;
        }

        if (ball.x + ball.width <= 0) {
            ball.x = (WIDTH/2)-12;
            ball.y = (HEIGHT/2)-12;

            deltaX = (-3 + rand.nextInt(6)) * speed;
            deltaY = (-3 + rand.nextInt(6)) * speed;

            while (deltaX == 0) {
                deltaX = (-3 + rand.nextInt(6)) * speed;
            }
            while (deltaY == 0) {
                deltaY = (-3 + rand.nextInt(6)) * speed;
            }

            score1++;
        }
        if (ball.x >= WIDTH) {
            ball.x = (WIDTH/2)-12;
            ball.y = (HEIGHT/2)-12;

            deltaX = (-3 + rand.nextInt(6)) * speed;
            deltaY = (-3 + rand.nextInt(6)) * speed;

            while (deltaX == 0) {
                deltaX = (-3 + rand.nextInt(6)) * speed;
            }
            while (deltaY == 0) {
                deltaY = (-3 + rand.nextInt(6)) * speed;
            }

            score2++;
        }

        ball.x += deltaX;
        ball.y += deltaY;
    }

    public void bot1() {
        if (ball.x <= WIDTH/2) {
            if (ball.y + ball.height / 2 > player2.y + player2.height / 2) {
                player2.y += speed * 2;
            } else {
                player2.y -= speed * 2;
            }
        }
    }
    public void bot2() {
        if (ball.x <= WIDTH/2) {
            if (ball.y + ball.height / 2 > player2.y + player2.height / 2) {
                player2.y += speed * 5;
            } else {
                player2.y -= speed * 5;
            }
        }
    }
    public void bot3() {
        if (ball.x <= WIDTH/2) {
            if (ball.y + ball.height / 2 > player2.y + player2.height / 2) {
                player2.y += speed * 8;
            } else {
                player2.y -= speed * 8;
            }
        }
    }

    public void repaint(Graphics g) {
        //clear
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //background
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        if (started) {
            g.setColor(Color.white);
            g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
        }

        //the score
        if (started) {
            g.setColor(Color.blue);
            g.setFont(new Font("Arial", Font.BOLD, 37));
            g.drawString(String.valueOf(score1), (WIDTH / 2) + 50, 50);
            g.setColor(Color.red);
            g.drawString(String.valueOf(score2), (WIDTH / 2) - 75, 50);
        }

        //do if game not started
        if (!started) {
            g.setColor((Color.white));
            g.setFont(new Font("Arial", Font.BOLD, 100));
            g.drawString("Pong", WIDTH / 2 - 125, HEIGHT / 3);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString("Press 'SPACE' to play with another player", WIDTH / 2 - 250, HEIGHT / 2);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString("'w' or 's' for red, 'up' or 'down' for blue", WIDTH / 2 - 100, HEIGHT / 2 + 50);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString("Press 'ENTER' to play with the computer", WIDTH / 2 - 250, HEIGHT / 2 + 100);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString("Pro tip : to vary the speed of the ball, hit the ball with the top or bottom edges of the paddle.", WIDTH / 2 - 262, HEIGHT / 2 + 250);
        }

        //the ball
        if (started) {
            g.setColor(Color.white);
            g.fillOval(ball.x, ball.y, ball.width, ball.height);
        }

        //the player/player1/blue
        g.setColor(Color.blue);
        g.fillRect(player1.x, player1.y, player1.width, player1.height);

        //the AI/player2/red
        g.setColor(Color.red);
        g.fillRect(player2.x, player2.y, player2.width, player2.height);

        if (paused) {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 100));
            g.drawString("PAUSED", WIDTH / 2 - 125, HEIGHT / 3);
        }

        if (bot && !(level == 1 || level == 2 || level == 3)) {
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString(" Press 1 for easy, 2 for hard, 3 for impossible.", WIDTH/2 - 275, HEIGHT/2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame ++;

        if (started && !paused) {

            if (!bot || ((level == 1 || level == 2 || level == 3))) {

                playerUpdate();

                ballUpdate();


                if (level == 1) {
                    bot1();
                }
                if (level == 2) {
                    bot2();
                }
                if (level == 3) {
                    bot3();
                }
            }
        }

        renderer.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (started) {
            if (!paused) {
                if (!bot) {
                    if (e.getKeyCode() == KeyEvent.VK_W) {
                        w = true;
                    }
                    if (e.getKeyCode() == KeyEvent.VK_S) {
                        s = true;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    up = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    down = true;
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                paused = !paused;
            }
        }


        if (!started) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                bot = false;
                started = true;
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                bot = true;
                started = true;
            }

        }

        if (bot && !(level ==1 || level == 2 || level == 3)) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_1 :
                    level = 1;
                    break;
                case KeyEvent.VK_2 :
                    level = 2;
                    break;
                case KeyEvent.VK_3 :
                    level = 3;
                    break;
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_W) {
            w = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            s = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = false;
        }
    }
}