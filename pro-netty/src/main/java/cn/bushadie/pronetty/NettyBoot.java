package cn.bushadie.pronetty;

import cn.bushadie.pronetty.websocket.WSServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author jdmy
 * on 2018/10/17.
 **/
@Component
public class NettyBoot implements ApplicationListener< ContextRefreshedEvent > {
    @Override
    public void onApplicationEvent( ContextRefreshedEvent event ) {
        if( event.getApplicationContext().getParent() == null ){
            try {
                WSServer.getInstance().start();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    }
}
