package com.jyg.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import com.jyg.bean.Player;

/**
 * created by jiayaoguang at 2017年12月12日
 */
public class PlayerCodec implements Codec<Player>{

	public PlayerCodec(){
	}
	
	//解码
	@Override
	public Player decode(BsonReader reader, DecoderContext decoderContext) {
		Player player = new Player();
		
		reader.readStartDocument();
		
		reader.readObjectId("_id");
		player.setGuid(reader.readInt64("guid"));;
		player.setName(reader.readString("name"));
		
		reader.readEndDocument();
		
		return player;
	}
	
	//编码
	@Override
	public void encode(BsonWriter writer, Player value,
			EncoderContext encoderContext) {
		
		writer.writeStartDocument();
		
		writer.writeInt64("guid", value.getGuid());
		writer.writeString("name",value.getName());
		
		writer.writeEndDocument();
		
	}

	
	@Override
	public Class<Player> getEncoderClass() {
		// TODO Auto-generated method stub
		return Player.class;
	}
	
}

