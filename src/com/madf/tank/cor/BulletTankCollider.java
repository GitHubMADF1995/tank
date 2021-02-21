package com.madf.tank.cor;

import com.madf.tank.Bullet;
import com.madf.tank.GameObject;
import com.madf.tank.Tank;

/**
 * 子弹和坦克的相撞
 */
public class BulletTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Tank) {
            Bullet bullet = (Bullet) o1;
            Tank tank = (Tank) o2;
            return !bullet.collideWith(tank);
        } else if (o1 instanceof Tank && o2 instanceof Bullet) {
            return collide(o2, o1);
        } else {
            return true;
        }
    }
}
