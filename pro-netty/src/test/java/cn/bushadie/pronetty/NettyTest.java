package cn.bushadie.pronetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.charset.Charset;

/**
 * @author jdmy
 * on 2018/10/15.
 **/
@SpringBootTest
@RunWith( SpringJUnit4ClassRunner.class )
public class NettyTest {

    @Test
    public void nettyTest() {
        NioEventLoopGroup parentGroup = new NioEventLoopGroup( 1 );
        NioEventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group( parentGroup, childGroup );
            serverBootstrap.channel( NioServerSocketChannel.class );
            serverBootstrap.option( ChannelOption.SO_BACKLOG, 128 )
                    .option( ChannelOption.SO_KEEPALIVE, true )
                    .childHandler( new ChannelInitializer< SocketChannel >() {
                        @Override
                        protected void initChannel( SocketChannel ch )
                                throws Exception {
                            ch.pipeline().addLast( new DelimiterBasedFrameDecoder( Integer.MAX_VALUE, Delimiters.lineDelimiter()[0] ) );
                            ch.pipeline().addLast( new SimpleHandler() );
                        }
                    } );
            ChannelFuture future = serverBootstrap.bind( 8080 ).sync();
            future.channel().closeFuture().sync();
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }

    }

    private class SimpleHandler extends ChannelHandlerAdapter {
        public void channelRead(ChannelHandlerContext ctx, Object msg)
                throws Exception {

            System.out.println("开始读取数据============");
            if(msg instanceof ByteBuf){
                ByteBuf req = ( ByteBuf )msg;
                String content = req.toString( Charset.defaultCharset());
                System.out.println(content);
                ctx.channel().writeAndFlush("李四\r\n");

            }


        }

        public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
                throws Exception {
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                throws Exception {
            // TODO Auto-generated method stub
            super.exceptionCaught(ctx, cause);
        }

    }

}
