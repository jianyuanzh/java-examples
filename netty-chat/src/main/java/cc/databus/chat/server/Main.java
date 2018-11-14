package cc.databus.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class Main {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                // 使用attr给服务端channel添加一些属性
                .attr(AttributeKey.newInstance("serverNameAttr"), "Netty Chat 1.0")
                // 用来处理Server启动过程中的逻辑，一般不需要使用
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {
                        System.out.println("Server is initializing ... ");
                    }
                })
                // 区别于channel。 childChannel用来处理新连接的读写
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        // handle the channel
                        nioSocketChannel.pipeline().addFirst(new FirstServerHandler());
                    }
                })
                // 给新连接添加属性
                .childAttr(AttributeKey.newInstance("childKey"), "childAttributeValue")
                // 设置与client连接的选项
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                // 设置server端channel，SO BACKLOG size
                .option(ChannelOption.SO_BACKLOG, 1024);


        bind(serverBootstrap, 8080);

    }

    private static void bind(final ServerBootstrap bootstrap, int port) {
        bootstrap.bind(port)
                .addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if (future.isSuccess()) {
                            System.out.println("Bind at port [" + port +"].");
                        }
                        else {
                            System.err.println("Bind port [" + port + "] failed!");
                            bind(bootstrap, port +1);
                        }
                    }
                });
    }
}
