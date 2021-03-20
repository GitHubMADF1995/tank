package com.madf.net;

import com.madf.tank.Dir;
import com.madf.tank.Group;
import com.madf.tank.Tank;
import com.madf.tank.TankFrame;

import java.io.*;
import java.util.UUID;

/**
 * 坦克加入界面的消息实体类
 */
public class TankJoinMsg extends Msg {
    public int x, y;
    public Dir dir;
    public boolean moving;
    public Group group;
    public UUID id;

    public TankJoinMsg() {
    }

    public TankJoinMsg(Tank tank) {
        this.x = tank.getX();
        this.y = tank.getY();
        this.dir = tank.getDir();
        this.group = tank.getGroup();
        this.id = tank.getId();
        this.moving = tank.isMoving();
    }

    public TankJoinMsg(int x, int y, Dir dir, boolean moving, Group group, UUID id) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.group = group;
        this.id = id;
    }

    /**
     * 将消息实体转为字节数组（编码）
     * @return
     */
    @Override
    public byte[] parseMsgToBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeInt(x);//4个字节
            dos.writeInt(y);//4个字节
            dos.writeInt(dir.ordinal());//Enum的下标int类型，4个字节
            dos.writeBoolean(moving);//1个字节
            dos.writeInt(group.ordinal());//Enum的下标int类型，4个字节
            dos.writeLong(id.getMostSignificantBits());//UUID的高8位，8个字节
            dos.writeLong(id.getLeastSignificantBits());//UUID的低8位，8个字节
            dos.flush();
            bytes = baos.toByteArray();
        } catch (Exception e) {
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

    /**
     * 将字节数组解析为消息类型实体（解码）
     * @param bytes
     */
    @Override
    public void parseBytesToMsg(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.moving = dis.readBoolean();
            this.group = Group.values()[dis.readInt()];
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
        if (this.id.equals(TankFrame.INSTANCE.getMainTank().getId()) || TankFrame.INSTANCE.findTankByUUID(this.id) != null) return;

        Tank tank = new Tank(this);
        TankFrame.INSTANCE.addTank(tank);

        //send a new TankJoinMsg to the joined tank
        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankJoin;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("uuid=" + id + "|")
                .append("x=" + x + "|")
                .append("y=" + y + "|")
                .append("moving=" + moving + "|")
                .append("dir=" + dir + "|")
                .append("group=" + group + "|")
                .append("]");
        return builder.toString();
    }
}
