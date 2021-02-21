package com.madf.tank;

import java.awt.*;

/**
 * 子弹
 */
public class Bullet extends GameObject {

    private static final int SPEED = 10;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();

    private int x, y;
    private Dir dir;

    private boolean living = true;

    GameModel gameModel = null;

    private Group group = Group.BAD;

    Rectangle rect = new Rectangle();

    public Bullet(int x, int y, Dir dir, Group group, GameModel gameModel) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.gameModel = gameModel;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;

        gameModel.addGameObject(this);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics graphics) {
        if (!living) {
            gameModel.removeGameObject(this);
        }
        switch (dir) {
            case LEFT:
                graphics.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case RIGHT:
                graphics.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case UP:
                graphics.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case DOWN:
                graphics.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }

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

        //更新rect
        rect.x = this.x;
        rect.y = this.y;

        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) living = false;
    }

    public boolean collideWith(Tank tank) {
        if (this.group == tank.getGroup()) return false;

        //TODO： 用一个rect来记录子弹的位置
//        Rectangle rect1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
//        Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);
        if (rect.intersects(tank.rect)) {
            tank.die();
            this.die();
            int eX = tank.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
            int eY = tank.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
            gameModel.addGameObject(new Explode(eX, eY, gameModel));
            return true;
        }

        return false;
    }

    private void die() {
        this.living = false;
    }
}
