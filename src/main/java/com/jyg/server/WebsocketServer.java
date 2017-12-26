package com.jyg.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jyg.consumers.EventConsumer;
import com.jyg.module.CoreModule;
import com.jyg.util.GlobalQueue;
import com.jyg.world.World;

/**
 * Created by jiayaoguang on 2017/3/30
 */
public class WebsocketServer {
	
	public static final Injector injetor = Guice.createInjector(new CoreModule());
	

    public void start() throws Exception {

    	World world = injetor.getInstance(World.class);
    	world.startWorld();
    	GlobalQueue.class.newInstance();
    }

    public static void main(String[] args) throws Exception {
        
        new WebsocketServer().start();

    }
}