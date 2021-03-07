package com.madf.tank.strategy;

import com.madf.tank.Tank;

import java.io.Serializable;

/**
 * 开火的策略接口
 */
public interface FireStrategy extends Serializable {
    void fire(Tank tank);
}
