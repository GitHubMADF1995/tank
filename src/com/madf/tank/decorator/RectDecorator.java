package com.madf.tank.decorator;

import com.madf.tank.GameObject;

import java.awt.*;

public class RectDecorator extends GODecorator {

    public RectDecorator(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void paint(Graphics graphics) {
        this.x = gameObject.x;
        this.y = gameObject.y;
        gameObject.paint(graphics);

        Color color = graphics.getColor();
        graphics.setColor(Color.YELLOW);
        graphics.drawRect(gameObject.x, gameObject.y, getWidth(), getHeight());
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
