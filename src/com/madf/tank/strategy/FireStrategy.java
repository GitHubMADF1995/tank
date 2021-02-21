package com.madf.tank.strategy;

import com.madf.tank.Tank;

/**
 * 开火的策略接口
 */
public interface FireStrategy {
    void fire(Tank tank);
}
