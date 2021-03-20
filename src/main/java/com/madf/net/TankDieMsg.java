package com.madf.net;

import com.madf.tank.Bullet;
import com.madf.tank.Tank;
import com.madf.tank.TankFrame;

import java.io.*;
import java.util.UUID;

/**
 * 坦克死掉的消息实体
 */
public class TankDieMsg extends Msg {
    public UUID bulletId;//who killed me
    public UUID id;

    public TankDieMsg() {}

    public TankDieMsg(UUID bulletId, UUID id) {
        this.bulletId = bulletId;
        this.id = id;
    }

    @Override
    public byte[] parseMsgToBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeLong(bulletId.getMostSignificantBits());//高8位
            dos.writeLong(bulletId.getLeastSignificantBits());//低8位
            dos.writeLong(id.getMostSignificantBits());//高8位
            dos.writeLong(id.getLeastSignificantBits());//低8位
            dos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(dos != null) {
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
            this.bulletId = new UUID(dis.readLong(), dis.readLong());
            this.id = new UUID(dis.readLong(), dis.readLong());
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
        System.out.println("We got a tank die:" + id);
        System.out.println("and my tank is:" + TankFrame.INSTANCE.getMainTank().getId());
        Tank tt = TankFrame.INSTANCE.findTankByUUID(id);
        System.out.println("I found a tank with this id:" + tt);

        Bullet b = TankFrame.INSTANCE.findBulletByUUID(bulletId);
        if (b != null) {
            b.die();
        }

        if (this.id.equals(TankFrame.INSTANCE.getMainTank().getId())) {
            TankFrame.INSTANCE.getMainTank().die();
        } else {
            Tank t = TankFrame.INSTANCE.findTankByUUID(id);
            if (t != null) {
                t.die();
            }
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankDie;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("bulletId=" + bulletId + "|")
                .append("id=" + id + "|")
                .append("]");
        return builder.toString();
    }
}
