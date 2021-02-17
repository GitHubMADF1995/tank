package com.madf.abstractfactory;

import com.madf.tank.Tank;

import java.awt.*;

public abstract class BaseBullet {
    public abstract void paint(Graphics graphics);

    public abstract void collideWith(Tank tank);
}
