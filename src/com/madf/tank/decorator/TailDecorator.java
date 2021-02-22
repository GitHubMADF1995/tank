package com.madf.tank.decorator;

import com.madf.tank.GameObject;

import java.awt.*;

public class TailDecorator extends GODecorator {

    public TailDecorator(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void paint(Graphics graphics) {
        this.x = gameObject.x;
        this.y = gameObject.y;
        gameObject.paint(graphics);

        Color color = graphics.getColor();
        graphics.setColor(Color.RED);
        graphics.drawLine(gameObject.x, gameObject.y, gameObject.x + getWidth(), gameObject.y + getHeight());
        graphics.setColor(color);
    }

    @Override
    public int getWidth() {
        return super.gameObject.getWidth();
    }

    @Override
    public int getHeight() {
        return super.gameObject.getHeight();
    }
}
