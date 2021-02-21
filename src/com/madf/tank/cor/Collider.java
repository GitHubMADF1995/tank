package com.madf.tank.cor;

import com.madf.tank.GameObject;

/**
 * 碰撞器（策略模式）
 */
public interface Collider {
    boolean collide(GameObject o1, GameObject o2);
}
