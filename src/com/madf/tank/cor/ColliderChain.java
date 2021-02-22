package com.madf.tank.cor;

import com.madf.tank.GameObject;

import java.util.LinkedList;
import java.util.List;

/**
 * 责任链模式的碰撞器
 * 它也可以实现Collider接口，作为一个chain与chain之间的碰撞
 */
public class ColliderChain implements Collider {
    private List<Collider> colliders = new LinkedList<>();

    public ColliderChain() {
        add(new BulletTankCollider());
        //也可以增加其他的碰撞类
        add(new TankTankCollider());
    }

    public void add(Collider c) {
        colliders.add(c);
    }

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        for (int i = 0; i < colliders.size(); i++) {
            if (!colliders.get(i).collide(o1, o2)) return false;
        }

        return true;
    }
}
