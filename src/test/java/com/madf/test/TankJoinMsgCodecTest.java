package com.madf.test;

import com.madf.net.*;
import com.madf.tank.Dir;
import com.madf.tank.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.UUID;

public class TankJoinMsgCodecTest {

    @Test
    public void testEncoder() {
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        Msg msg = new TankJoinMsg(5, 10, Dir.DOWN, true, Group.BAD, id);
        channel.pipeline().addLast(new MsgEncoder());
        //写出去
        channel.writeOutbound(msg);
        //读出来
        ByteBuf buf = channel.readOutbound();

        //消息类型
        MsgType msgType = MsgType.values()[buf.readInt()];
        assertEquals(MsgType.TankJoin, msgType);

        int length = buf.readInt();
        assertEquals(33, length);//x:4,y:4,dir:4,moving:1,group:4,id:高8/低8

        int x = buf.readInt();
        int y = buf.readInt();
        int dirOrdinal = buf.readInt();
        Dir dir = Dir.values()[dirOrdinal];
        boolean moving = buf.readBoolean();
        int groupOrdinal = buf.readInt();
        Group group = Group.values()[groupOrdinal];
        UUID uuid = new UUID(buf.readLong(), buf.readLong());

        assertEquals(5, x);
        assertEquals(10, y);
        assertEquals(Dir.DOWN, dir);
        assertEquals(true, moving);
        assertEquals(Group.BAD, group);
        assertEquals(id, uuid);
    }

    @Test
    public void testDecoder() {
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(5, 10, Dir.DOWN, true, Group.BAD, id);
        channel.pipeline().addLast(new MsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankJoin.ordinal());
        byte[] bytes = msg.parseMsgToBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        channel.writeInbound(buf.duplicate());
        TankJoinMsg msgRead = channel.readInbound();

        assertEquals(5, msgRead.x);
        assertEquals(10, msgRead.y);
        assertEquals(Dir.DOWN, msgRead.dir);
        assertEquals(true, msgRead.moving);
        assertEquals(Group.BAD, msgRead.group);
        assertEquals(id, msgRead.id);
    }

}
