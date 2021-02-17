package com.madf.tank;

import com.madf.abstractfactory.BaseExplode;

import java.awt.*;

/**
 * 爆炸物
 */
public class Explode extends BaseExplode {

    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();

    private int x, y;

    private boolean living = true;
    TankFrame tankFrame = null;

    private int step = 0;

    public Explode(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.drawImage(ResourceMgr.explodes[step++], x, y, null);
        if (step >= ResourceMgr.explodes.length) tankFrame.explodes.remove(this);
    }

}
