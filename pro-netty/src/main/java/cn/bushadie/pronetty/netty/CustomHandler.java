package cn.bushadie.pronetty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author jdmy
 * on 2018/10/16.
 * 创建自定义助手类
 **/
// SimpleChannelInboundHandler 对于请求来讲 相当于[入站,入境]
public class CustomHandler extends SimpleChannelInboundHandler< HttpObject > {
    @Override
    protected void channelRead0( ChannelHandlerContext ctx, HttpObject msg ) throws Exception {
        // 获取channel
        Channel channel = ctx.channel();

        if( msg instanceof HttpRequest ){
            //显示客户端远程地址
            System.out.println( channel.remoteAddress() );

            // 定义发送的数据消息
            ByteBuf content = Unpooled.copiedBuffer( "hello netty ~~", CharsetUtil.UTF_8 );

            //构建Http response
            DefaultFullHttpResponse response = new DefaultFullHttpResponse( HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content );
            // 为响应增加数据类型和长度
            response.headers().set( HttpHeaderNames.CONTENT_TYPE,"text/plain" );
            response.headers().set( HttpHeaderNames.CONTENT_LENGTH,content.readableBytes() );

            // 写入,并且刷新到服务端
            ctx.writeAndFlush( response );
        }

    }

    @Override
    public void channelRegistered( ChannelHandlerContext ctx ) throws Exception {
        System.out.println( "channel------注册" );
        super.channelRegistered( ctx );
    }

    @Override
    public void channelUnregistered( ChannelHandlerContext ctx ) throws Exception {
        System.out.println( "channel------移除" );
        super.channelUnregistered( ctx );
    }

    @Override
    public void channelActive( ChannelHandlerContext ctx ) throws Exception {
        System.out.println( "channel------活跃" );
        super.channelActive( ctx );
    }

    @Override
    public void channelInactive( ChannelHandlerContext ctx ) throws Exception {
        System.out.println( "channel------不活跃" );
        super.channelInactive( ctx );
    }

    @Override
    public void channelReadComplete( ChannelHandlerContext ctx ) throws Exception {
        System.out.println( "channel------读取完毕" );
        super.channelReadComplete( ctx );
    }

    @Override
    public void userEventTriggered( ChannelHandlerContext ctx, Object evt ) throws Exception {
        System.out.println( "channel------用户事件触发" );
        super.userEventTriggered( ctx, evt );
    }

    @Override
    public void channelWritabilityChanged( ChannelHandlerContext ctx ) throws Exception {
        System.out.println( "channel------可写更改" );
        super.channelWritabilityChanged( ctx );
    }

    @Override
    public void handlerAdded( ChannelHandlerContext ctx ) throws Exception {
        System.out.println( "channel------助手类添加" );
        super.handlerAdded( ctx );
    }

    @Override
    public void handlerRemoved( ChannelHandlerContext ctx ) throws Exception {
        System.out.println( "channel-------助手类移除" );
        super.handlerRemoved( ctx );
    }

    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception {
        System.out.println( "channel------捕获到异常" );
        super.exceptionCaught( ctx, cause );
    }
}
