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
}
