package com.madf.tank.cor;

import com.madf.tank.Bullet;
import com.madf.tank.Explode;
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
            //将子弹的碰撞方法移到此，以降低耦合即解耦
            if (bullet.getGroup() == tank.group) {
                return true;
            }
            if (bullet.rect.intersects(tank.rect)) {
                tank.die();
                bullet.die();
                int eX = tank.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
                int eY = tank.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
                bullet.gameModel.addGameObject(new Explode(eX, eY, bullet.gameModel));
                return false;
            }
            return true;
        } else if (o1 instanceof Tank && o2 instanceof Bullet) {
            return collide(o2, o1);
        } else {
            return true;
        }
    }
}
