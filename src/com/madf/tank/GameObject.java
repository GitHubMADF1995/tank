package com.madf.tank;

import java.awt.*;

/**
 * Mediator调停者模式，所有的游戏物体继承自它
 */
public abstract class GameObject {

    public int x, y;

    public abstract void paint(Graphics graphics);

    public abstract int getWidth();

    public abstract int getHeight();
}
