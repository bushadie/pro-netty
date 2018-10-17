package cn.bushadie.pronetty.websocket;

import com.sun.prism.shader.DrawPgram_ImagePattern_Loader;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author jdmy
 * on 2018/10/17.
 **/
public class WSServerInitializer extends ChannelInitializer< SocketChannel > {
    @Override
    protected void initChannel( SocketChannel ch ) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast( new HttpServerCodec(  ) );
        // 大数据流
        pipeline.addLast( new ChunkedWriteHandler(  ) );
        // 聚合器,   处理消息,后面是长度  对httpMessage,聚合成FullHTTPRequest和FullHttpResponse
        // 几乎对netty中的编程,都会使用到此handler
        pipeline.addLast( new HttpObjectAggregator( 1024*64 ) );
        //=============================  以上用于支持http协议

        /**
         * web socket  服务器处理的协议,  用于指定给客户端连接访问的路由  /ws
         * 本handler会帮你处理一些繁重复杂的事
         * 帮你处理握手工作  handshaking(close,ping,pong) ping+pong=心跳
         * 对于web socket,都是以 frames进行传输的,不同的数据类型对应的frames也不同
         */

        pipeline.addLast( new WebSocketServerProtocolHandler( "/ws" ) );

        // 自定义的handler
        pipeline.addLast( new ChatHandler() );
    }
}
