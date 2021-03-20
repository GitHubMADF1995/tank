package com.madf.tank;

import com.madf.net.Client;
import com.madf.net.TankDieMsg;

import java.awt.*;
import java.util.UUID;

/**
 * 子弹
 */
public class Bullet {

    private static final int SPEED = 6;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();

    private int x, y;
    private Dir dir;
    private UUID id = UUID.randomUUID();
    private UUID playerId;

    private boolean living = true;

    TankFrame tankFrame = null;

    private Group group = Group.BAD;

    Rectangle rect = new Rectangle();

    public Bullet(UUID playerId, int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public void paint(Graphics graphics) {
        if (!living) {
            tankFrame.bullets.remove(this);
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

    public void collideWith(Tank tank) {
//        if (this.group == tank.getGroup()) return;
        if (this.playerId.equals(tank.getId())) return;

        //TODO： 用一个rect来记录子弹的位置
//        Rectangle rect1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
//        Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);
        if (living && tank.isLiving() && rect.intersects(tank.rect)) {
            tank.die();
            this.die();
//            int eX = tank.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
//            int eY = tank.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
//            tankFrame.explodes.add(new Explode(eX, eY));
            Client.INSTANCE.send(new TankDieMsg(this.id, tank.getId()));
        }
    }

    public void die() {
        this.living = false;
    }
}
