package com.api.iqtec.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Transporte;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



@Service
public class RedisTransportUtility {

	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	Gson gson;
	
	private final String REDISTRANSPORTKEY = "transports";
	
	
	public void setValues(List<Transporte> list) {
		String valuesJson = gson.toJson(list.toArray(), new TypeToken<Transporte[]>() {}.getType());
		
		redisTemplate.opsForValue().set(REDISTRANSPORTKEY, valuesJson);
		redisTemplate.expire(REDISTRANSPORTKEY, 400, TimeUnit.MINUTES);
	}
	
	public  List<Transporte> getValues(){
		List<Transporte> valuesInRedis = new ArrayList<Transporte>();
		
		Transporte array[] = gson.fromJson(redisTemplate.opsForValue().get(REDISTRANSPORTKEY), new TypeToken<Transporte[]>() {}.getType());
		
		if(array == null) {
			valuesInRedis = new ArrayList<Transporte>();
		}else {
			valuesInRedis = new ArrayList<Transporte>(Arrays.asList(array));

		}
		
		return valuesInRedis;
	}
	
	public void updateRedisCache(Transporte object, String operation) {
		List<Transporte> listOfRedisValues = getValues();
		
		
		deleteKeyfromRedis();
		
		if(operation.equals("delete")) {
			listOfRedisValues.remove(object);
		}else if(operation.equals("insert")){
			
			listOfRedisValues.add(object);
		}else {
			listOfRedisValues.remove(object);
			listOfRedisValues.add(object);
		}
		
		setValues(listOfRedisValues);
	}
	
	
	public void deleteKeyfromRedis() {
		redisTemplate.delete(REDISTRANSPORTKEY);
	}

}
