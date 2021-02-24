package com.madf.tank;

/**
 * 观察者
 */
public abstract class TankFireObserver {
    abstract void actionOnFire(TankFireEvent event);
}
