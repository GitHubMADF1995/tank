package com.madf.tank;

/**
 * 朝四个方向开火的策略
 */
public class FourDirFireStrategy implements FireStrategy {
    @Override
    public void fire(Tank tank) {
        int bX = tank.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int bY = tank.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;

        Dir[] dirs = Dir.values();
        for (Dir dir : dirs) {
            new Bullet(bX, bY, dir, tank.group, tank.gameModel);
        }
        if (tank.group == Group.GOOD) new Thread(() -> new Audio("audio/explode.wav").loop()).start();
    }
}