package cn.bushadie.pronetty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author jdmy
 * on 2018/10/15.
 * 实现客户端发送一个请求,服务器会返回hello netty
 **/
public class HelloServer {
    public static void main( String[] args ) throws Exception {
        // 定义一对线程组
        // 用于接受客户端的连接,但是不做任何处理
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // bossGroup  会把工作丢给它
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // netty 的服务器的创建   serverBootstrap是一个启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group( bossGroup, workerGroup )  // 设置组从线程组
                    .channel( NioServerSocketChannel.class )  // 设置nio的双向通道
                    .childHandler( new HelloServerInitializer() ); // 子处理器,用于处理  workerGroup

            //启动server,并且设置8088为启动的端口号,  同时启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind( 8088 ).sync();// 绑定端口,使用同步的方式

            // 监听关闭的channel,  设置位同步方式为同步
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
