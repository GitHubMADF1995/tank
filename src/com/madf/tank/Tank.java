package com.madf.tank;

import java.awt.*;

/**
 * 坦克
 */
public class Tank {

    private int x, y;
    private Dir dir = Dir.DOWN;
    private static final int SPEED = 5;

    public static int WIDTH = ResourceMgr.tankD.getWidth();
    public static int HEIGHT = ResourceMgr.tankD.getHeight();

    private boolean moving = false;
    private boolean living = true;

    private TankFrame tankFrame = null;

    public Tank(int x, int y, Dir dir, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
    }

    //画笔对象
    public void paint(Graphics graphics) {
        if (!living) tankFrame.enemyTanks.remove(this);

        switch (dir) {
            case LEFT:
                graphics.drawImage(ResourceMgr.tankL, x, y, null);
                break;
            case RIGHT:
                graphics.drawImage(ResourceMgr.tankR, x, y, null);
                break;
            case UP:
                graphics.drawImage(ResourceMgr.tankU, x, y, null);
                break;
            case DOWN:
                graphics.drawImage(ResourceMgr.tankD, x, y, null);
                break;
        }
        move();
    }

    public void move() {
        if (!moving) return;

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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public static int getSPEED() {
        return SPEED;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void fire() {
        int bX = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int bY = this.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
        tankFrame.bullets.add(new Bullet(bX, bY, this.dir, this.tankFrame));
    }

    public void die() {
        this.living = false;
    }
}
