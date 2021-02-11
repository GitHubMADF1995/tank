package com.madf.tank;

import java.awt.*;

/**
 * 子弹
 */
public class Bullet {

    private static final int SPEED = 1;
    private static int WIDTH = 30, HEIGHT = 30;

    private int x, y;
    private Dir dir;

    public Bullet(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void paint(Graphics graphics) {
        Color color = graphics.getColor();
        graphics.setColor(Color.RED);
        graphics.fillOval(x, y, WIDTH, HEIGHT);
        graphics.setColor(color);

        move();
    }

    public void move() {
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
        }
    }

}
