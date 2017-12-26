package com.jyg.mrg;

import org.bson.Document;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.jyg.bean.Player;
import com.jyg.module.CoreModule;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * created by jiayaoguang at 2017年12月12日
 */
public class MongoDBMrg extends AbstractMongoDBMrg{

	MongoClient client;
	MongoDatabase db;
	
	@Inject
	public MongoDBMrg() {
		client = createMongoClient();
		db = client.getDatabase("school");
	}
	
	public static void main(String[] args) {
		CoreModule module = new CoreModule();
		Injector injector = Guice.createInjector(module);
		MongoDBMrg mongodbMrg = injector.getInstance(MongoDBMrg.class);
		
		mongodbMrg.db.createCollection("classA");
		
		Player player = new Player();
		player.setGuid(11111);
		player.setName("rock");
		
		MongoCollection<Player> coll = mongodbMrg.db.getCollection("classA",Player.class);
		coll.insertOne(player);
		
		mongodbMrg.client.close();
	}
	
}

