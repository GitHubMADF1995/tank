package com.madf.net;

import com.madf.tank.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

/**
 * 客户端
 */
public class Client {
    public static final Client INSTANCE = new Client();
    private Client() {}

    private Channel channel = null;//用该channel进行传输

    public void connect() {
        //线程池
        EventLoopGroup group = new NioEventLoopGroup(1);

        Bootstrap b = new Bootstrap();

        try {
            ChannelFuture f = b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer())
                    .connect("localhost", 8888);

            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        System.out.println("client not connected....");
                    } else {
                        System.out.println("client connected....");
                        channel = future.channel();
                    }
                }
            });

            f.sync();
            f.channel().closeFuture().sync();
            System.out.println("Client connection closed....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void send(Msg msg) {
        System.out.println("Client send: " + msg);
        channel.writeAndFlush(msg);
    }
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new MsgEncoder())
                .addLast(new MsgDecoder())
//                .addLast(new ClientHandler());
                .addLast(new ClientMsgHandler());
    }
}

class ClientMsgHandler extends SimpleChannelInboundHandler<Msg> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Msg msg) throws Exception {
        //Client读Server的消息，进行处理
        System.out.println("Client receiver from Server: " + msg);
        msg.handle();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //初始化时加入主战坦克
        ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
    }
}

class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //channel第一次连上server可用，写出一个字符串
//        ByteBuf buf = Unpooled.copiedBuffer("hello, Server".getBytes());
//        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = null;
        try {
            buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            String msgAccepted = new String(bytes);
        } finally {
            //释放对它的引用
            if (buf != null) {
                ReferenceCountUtil.release(buf);
            }
        }
    }
}
