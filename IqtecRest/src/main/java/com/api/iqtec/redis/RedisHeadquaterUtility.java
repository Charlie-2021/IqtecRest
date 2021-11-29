package com.api.iqtec.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Sede;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



@Service
public class RedisHeadquaterUtility {

	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	Gson gson;
	
	private final String REDISHEADQUATERKEY = "headquaters";
	
	
	public void setValues(List<Sede> list) {
		String valuesJson = gson.toJson(list.toArray(), new TypeToken<Sede[]>() {}.getType());
		
		redisTemplate.opsForValue().set(REDISHEADQUATERKEY, valuesJson);
		redisTemplate.expire(REDISHEADQUATERKEY, 400, TimeUnit.MINUTES);
	}
	
	public  List<Sede> getValues(){
		List<Sede> valuesInRedis = new ArrayList<Sede>();
		
		Sede array[] = gson.fromJson(redisTemplate.opsForValue().get(REDISHEADQUATERKEY), new TypeToken<Sede[]>() {}.getType());
		
		if(array == null) {
			valuesInRedis = new ArrayList<Sede>();
		}else {
			valuesInRedis = new ArrayList<Sede>(Arrays.asList(array));

		}
		
		return valuesInRedis;
	}
	
	public void updateRedisCache(Sede object, String operation) {
		List<Sede> listOfRedisValues = getValues();
		
		
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
		redisTemplate.delete(REDISHEADQUATERKEY);
	}

}
