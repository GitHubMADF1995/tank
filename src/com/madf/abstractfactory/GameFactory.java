package com.madf.abstractfactory;

import com.madf.tank.Dir;
import com.madf.tank.Group;
import com.madf.tank.TankFrame;

public abstract class GameFactory {
    public abstract BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tankFrame);
    public abstract BaseExplode createExplode(int x, int y, TankFrame tankFrame);
    public abstract BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tankFrame);
}
