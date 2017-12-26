package com.jyg.module;

import javax.inject.Scope;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.jyg.consumers.EventConsumer;
import com.jyg.mrg.ChannelMrg;
import com.jyg.mrg.ClientSessionMrg;
import com.jyg.mrg.EventLoopGroupMrg;
import com.jyg.mrg.MongoDBMrg;
import com.jyg.mrg.TcpServiceMrg;
import com.jyg.world.World;

/**
 * created by jiayaoguang at 2017年12月6日
 */
public class CoreModule extends AbstractModule{

	@Override
	protected void configure() {
		binder().requireExplicitBindings();
		configCoreModule();
	}
	
	private void configCoreModule(){
		
		this.bind(ChannelMrg.class).in(Scopes.SINGLETON);
		this.bind(MongoDBMrg.class).in(Scopes.SINGLETON);
		this.bind(EventLoopGroupMrg.class).in(Scopes.SINGLETON);
		this.bind(ClientSessionMrg.class).in(Scopes.SINGLETON);
		this.bind(TcpServiceMrg.class).in(Scopes.SINGLETON);
		this.bind(World.class).in(Scopes.SINGLETON);
		
		
	}

}

