package com.madf.tank.cor;

import com.madf.tank.Bullet;
import com.madf.tank.GameObject;
import com.madf.tank.Wall;

/**
 * 子弹与墙的相撞
 */
public class BulletWallCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Wall) {
            Bullet b = (Bullet) o1;
            Wall w = (Wall) o2;
            if (b.rect.intersects(w.rect)) {
                b.die();
            }
        } else if (o1 instanceof Wall && o2 instanceof Bullet) {
            collide(o2, o1);
        }
        return true;
    }
}
