package com.madf.tank;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

    Tank myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD, this);
    java.util.List<Bullet> bullets = new ArrayList<>();
    java.util.List<Tank> enemyTanks = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();

    public GameModel() {
        int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));

        //初始化敌方坦克
        for (int i = 0; i < initTankCount; i++) {
            enemyTanks.add(new Tank(50 + i*80, 200, Dir.DOWN, Group.BAD, this));
        }
    }

    public void paint(Graphics graphics) {
        Color color = graphics.getColor();
        graphics.setColor(Color.WHITE);
        graphics.drawString("子弹的数量：" + bullets.size(), 10, 60);
        graphics.drawString("敌人的数量：" + enemyTanks.size(), 10, 80);
        graphics.drawString("爆炸的数量：" + explodes.size(), 10, 100);
        graphics.setColor(color);
        myTank.paint(graphics);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(graphics);
        }
        for (int i = 0; i < enemyTanks.size(); i++) {
            enemyTanks.get(i).paint(graphics);
        }

        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(graphics);
        }

        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < enemyTanks.size(); j++) {
                bullets.get(i).collideWith(enemyTanks.get(j));
            }
        }
    }

    public Tank getMainTank() {
        return myTank;
    }
}
