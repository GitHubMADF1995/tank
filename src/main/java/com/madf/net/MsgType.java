package com.madf.net;

/**
 * 消息类型
 */
public enum MsgType {
    TankJoin,
    TankDie,
    TankDirChanged,
    TankStartMoving,
    TankStop,
    BulletNew,
    BulletDie,
    ClientClose;
}
