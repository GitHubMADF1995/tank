package com.madf.tank;

public class TankFireHandler extends TankFireObserver {
    @Override
    void actionOnFire(TankFireEvent event) {
        Tank t = event.getSource();
        t.fire();
    }
}
