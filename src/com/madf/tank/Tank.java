package com.madf.tank;

import java.awt.*;
import java.util.Random;

/**
 * 坦克
 */
public class Tank {

    private int x, y;
    private Dir dir = Dir.DOWN;
    private static final int SPEED = 5;

    public static int WIDTH = ResourceMgr.tankD.getWidth();
    public static int HEIGHT = ResourceMgr.tankD.getHeight();

    private boolean moving = true;
    private boolean living = true;
    private Group group = Group.BAD;

    private Random random = new Random();

    private TankFrame tankFrame = null;

    public Tank(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
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

        if (this.group == Group.BAD && random.nextInt(100) > 95) this.fire();
        if (this.group == Group.BAD && random.nextInt(100) > 95) randomDir();
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void fire() {
        int bX = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int bY = this.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
        tankFrame.bullets.add(new Bullet(bX, bY, this.dir, this.group, this.tankFrame));
    }

    public void die() {
        this.living = false;
    }
}
