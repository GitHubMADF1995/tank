package com.madf.tank;

import java.io.Serializable;

/**
 * 观察者
 */
public abstract class TankFireObserver implements Serializable {
    abstract void actionOnFire(TankFireEvent event);
}
