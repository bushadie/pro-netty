package cn.bushadie.pronetty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author jdmy
 * on 2018/10/17.
 **/
public class WSServer {
    public static void main( String[] args ) throws InterruptedException {
        NioEventLoopGroup mainGroup = new NioEventLoopGroup();
        NioEventLoopGroup subGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group( mainGroup,subGroup )
            .channel( NioServerSocketChannel.class )
            .childHandler( new WSServerInitializer() );
            ChannelFuture channelFuture = server.bind( 8088 ).sync();

            channelFuture.channel().closeFuture().sync();
        } finally {
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }
}
