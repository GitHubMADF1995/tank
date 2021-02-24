package com.madf.tank;

/**
 * 坦克开火事件
 */
public class TankFireEvent {
    Tank tank;

    public TankFireEvent(Tank tank) {
        this.tank = tank;
    }

    public Tank getSource() {
        return tank;
    }
}
