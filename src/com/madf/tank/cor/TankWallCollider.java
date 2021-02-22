package com.madf.tank.cor;

import com.madf.tank.GameObject;
import com.madf.tank.Tank;
import com.madf.tank.Wall;

/**
 * 坦克与墙的相撞
 */
public class TankWallCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Wall) {
            Tank t = (Tank) o1;
            Wall w = (Wall) o2;
            if (t.rect.intersects(w.rect)) {
                t.back();
            }
        } else if (o1 instanceof Wall && o2 instanceof Tank) {
            collide(o2, o1);
        }
        return true;
    }
}
