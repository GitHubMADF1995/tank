package com.madf.tank.decorator;

import com.madf.tank.GameObject;

import java.awt.*;

public abstract class GODecorator extends GameObject {

    GameObject gameObject;

    public GODecorator(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public abstract void paint(Graphics graphics);
}
