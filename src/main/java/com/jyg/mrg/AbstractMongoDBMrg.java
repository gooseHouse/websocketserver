package com.jyg.mrg;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.jyg.bean.Player;
import com.jyg.codec.PlayerCodec;
import com.jyg.module.CoreModule;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

/**
 * created by jiayaoguang at 2017年12月12日
 */
public abstract class AbstractMongoDBMrg {

	public AbstractMongoDBMrg() {

	}

	protected MongoClient createMongoClient(){
		
		List<MongoCredential> credentialList = new ArrayList<>();
		
		MongoCredential credential = MongoCredential.createCredential("stone", "school", "123654asd".toCharArray());
		credentialList.add(credential);
		
		ServerAddress addr = new ServerAddress("localhost", 27017);
		
		MongoClientOptions mongoClientOptions = constructMongoClient();
		
		MongoClient mongoClient = new MongoClient(addr , mongoClientOptions);
		
		return mongoClient;
	}

	protected MongoClientOptions constructMongoClient() {

		CodecRegistry codecRegistry = CodecRegistries.fromRegistries( 
				MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(new PlayerCodec()),
				CodecRegistries.fromProviders(new CodecProvider(){

					@Override
					public <T> Codec<T> get(Class<T> clazz,
							CodecRegistry registry) {
						
						if(clazz == Player.class){
							return (Codec<T>) new PlayerCodec();
						}
						
						return null;
					}
					
				})
				);

		MongoClientOptions mongoClientOptions = new MongoClientOptions.Builder()
				.codecRegistry(codecRegistry).socketKeepAlive(true)
				
//				.connectionsPerHost(100)//最大连接数
//				
//				.minConnectionsPerHost(20)//最小连接数
//				
//				.maxWaitTime(200)//最大连接等待时间
//				
//				.maxConnectionIdleTime(600000)//最大连接闲置时间
				
				
				.build();
		
		

		return mongoClientOptions;
	}
	
}
