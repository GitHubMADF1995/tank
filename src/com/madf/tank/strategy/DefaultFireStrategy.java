package com.madf.tank.strategy;

import com.madf.tank.*;
import com.madf.tank.decorator.RectDecorator;
import com.madf.tank.decorator.TailDecorator;

/**
 * 默认开火策略方式
 */
public class DefaultFireStrategy implements FireStrategy {
    @Override
    public void fire(Tank tank) {
        int bX = tank.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int bY = tank.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
//        new Bullet(bX, bY, tank.dir, tank.group);
        GameModel.getInstance().addGameObject(new RectDecorator(new TailDecorator(new Bullet(bX, bY, tank.dir, tank.group))));
        if (tank.group == Group.GOOD) new Thread(() -> new Audio("audio/explode.wav").loop()).start();
    }
}