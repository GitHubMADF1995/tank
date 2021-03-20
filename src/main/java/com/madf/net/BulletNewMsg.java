package com.madf.net;

import com.madf.tank.Bullet;
import com.madf.tank.Dir;
import com.madf.tank.Group;
import com.madf.tank.TankFrame;

import java.io.*;
import java.util.UUID;

/**
 * 子弹发射的消息是实体类
 */
public class BulletNewMsg extends Msg {

    public UUID playerId;
    public UUID id;
    public int x, y;
    public Dir dir;
    public Group group;

    public BulletNewMsg() {
    }

    public BulletNewMsg(UUID playerId, UUID id, int x, int y, Dir dir, Group group) {
        this.playerId = playerId;
        this.id = id;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
    }

    public BulletNewMsg(Bullet bullet) {
        this.playerId = bullet.getPlayerId();
        this.id = bullet.getId();
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.dir = bullet.getDir();
        this.group = bullet.getGroup();
    }

    @Override
    public byte[] parseMsgToBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            //先写主战坦克ID
            dos.writeLong(playerId.getMostSignificantBits());
            dos.writeLong(playerId.getLeastSignificantBits());
            //再写子弹的ID
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
            dos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (dos != null) {
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }

    @Override
    public void parseBytesToMsg(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.playerId = new UUID(dis.readLong(), dis.readLong());
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.group = Group.values()[dis.readInt()];
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handle() {
        if (this.playerId.equals(TankFrame.INSTANCE.getMainTank().getId())) return;

        Bullet bullet = new Bullet(this.playerId, x, y, dir, group, TankFrame.INSTANCE);
        bullet.setId(this.id);
        TankFrame.INSTANCE.addBullet(bullet);
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.BulletNew;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("playerId=" + playerId + "|")
                .append("id=" + id + "|")
                .append("x=" + x + "|")
                .append("y=" + y + "|")
                .append("dir=" + dir + "|")
                .append("group=" + group + "|")
                .append("]");
        return builder.toString();
    }
}
