package cc.databus.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.TimeUnit;

public class ClientMain {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        // handle the channel here
                        nioSocketChannel.pipeline().addLast(new FirstClientHandler());
                    }
                })
                .attr(AttributeKey.newInstance("clientAttr"), "clientAttrVal")
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true);

        connect(bootstrap, "localhost", 8080, 0);
    }

    private static void connect(Bootstrap bootstrap, String remote, int port, int retry) {
        bootstrap.connect(remote, port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Connect successfully!");
                }
                else {
                    if (retry > 5) {
                        System.err.println("Failed to connect remote server in 5 times");
                    }
                    else {
                        System.err.println("Failed to connect remote server, retried " + retry + " times. ");
                        bootstrap.config().group().schedule(() -> connect(bootstrap, remote, port, retry + 1), 2, TimeUnit.SECONDS);
                    }
                }
            }
        });
    }
}
