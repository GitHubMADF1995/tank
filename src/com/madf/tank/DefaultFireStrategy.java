package com.madf.tank;

/**
 * 默认开火策略方式
 */
public class DefaultFireStrategy implements FireStrategy {
    @Override
    public void fire(Tank tank) {
        int bX = tank.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int bY = tank.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
        new Bullet(bX, bY, tank.dir, tank.group, tank.gameModel);
        if (tank.group == Group.GOOD) new Thread(() -> new Audio("audio/explode.wav").loop()).start();
    }
}