package cn.bushadie.pronetty.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author jdmy
 * on 2018/10/16.
 * 初始化器,channel注册后,会执行里面的相应初始化的方法
 **/
public class HelloServerInitializer extends ChannelInitializer< SocketChannel > {
    @Override
    protected void initChannel( SocketChannel channel ) throws Exception {
        // 通过channel去获得对应的管道
        ChannelPipeline pipeline = channel.pipeline();

        // 通过管道添加handler
        // HttpServerCodec   netty自己提供的助手类,可以理解为拦截器
        // 当请求来到服务器,我们需要做编解码响应到客户端做编码
        pipeline.addLast( "HttpServerCodec",new HttpServerCodec() );

        // 添加自定义的注解类助手,返回  "hello netty"
        pipeline.addLast( "customHandler" ,null);
    }
}
