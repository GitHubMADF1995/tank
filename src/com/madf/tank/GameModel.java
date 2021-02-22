package com.madf.tank;

import com.madf.tank.cor.BulletTankCollider;
import com.madf.tank.cor.Collider;
import com.madf.tank.cor.ColliderChain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Facade门面模式，负责Model和View中的Model
 */
public class GameModel {

    private static final GameModel INSTANCE = new GameModel();

    Tank myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD, this);
//    java.util.List<Bullet> bullets = new ArrayList<>();
//    java.util.List<Tank> enemyTanks = new ArrayList<>();
//    List<Explode> explodes = new ArrayList<>();

    List<GameObject> gameObjects = new ArrayList<>();

//    Collider collider = new BulletTankCollider();
    ColliderChain colliderChain = new ColliderChain();

    public static GameModel getInstance() {
        return INSTANCE;
    }

    private GameModel() {
        int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));

        //初始化敌方坦克
        for (int i = 0; i < initTankCount; i++) {
            addGameObject(new Tank(50 + i*80, 200, Dir.DOWN, Group.BAD, this));
        }

        //初始化墙
        addGameObject(new Wall(150, 150, 200, 50));
        addGameObject(new Wall(550, 150, 200, 50));
        addGameObject(new Wall(300, 300, 50, 200));
        addGameObject(new Wall(500, 300, 50, 200));
    }

    public void addGameObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        this.gameObjects.remove(gameObject);
    }

    public void paint(Graphics graphics) {
        Color color = graphics.getColor();
        graphics.setColor(Color.WHITE);
//        graphics.drawString("子弹的数量：" + bullets.size(), 10, 60);
//        graphics.drawString("敌人的数量：" + enemyTanks.size(), 10, 80);
//        graphics.drawString("爆炸的数量：" + explodes.size(), 10, 100);
        graphics.setColor(color);
        myTank.paint(graphics);
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).paint(graphics);
        }

        //互相碰撞校验
        for (int i = 0; i < gameObjects.size(); i++) {
            for (int j = i + 1; j < gameObjects.size(); j++) {
                GameObject o1 = gameObjects.get(i);
                GameObject o2 = gameObjects.get(j);
//                collider.collide(o1, o2);
                colliderChain.collide(o1, o2);
            }
        }
    }

    public Tank getMainTank() {
        return myTank;
    }
}
