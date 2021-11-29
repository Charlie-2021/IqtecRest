package com.api.iqtec.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Cliente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



@Service
public class RedisClientUtility {

	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	Gson gson;
	
	private final String REDISCLIENTKEY = "clients";
	
	
	public void setValues(List<Cliente> list) {
		String valuesJson = gson.toJson(list.toArray(), new TypeToken<Cliente[]>() {}.getType());
		
		redisTemplate.opsForValue().set(REDISCLIENTKEY, valuesJson);
		redisTemplate.expire(REDISCLIENTKEY, 400, TimeUnit.MINUTES);
	}
	
	public  List<Cliente> getValues(){
		List<Cliente> valuesInRedis = new ArrayList<Cliente>();
		
		 Cliente array[] = gson.fromJson(redisTemplate.opsForValue().get(REDISCLIENTKEY), new TypeToken<Cliente[]>() {}.getType());
		
		if(array == null) {
			valuesInRedis = new ArrayList<Cliente>();
		}else {
			valuesInRedis = new ArrayList<Cliente>(Arrays.asList(array));

		}
		
		return valuesInRedis;
	}
	
	public void updateRedisCache(Cliente object, String operation) {
		List<Cliente> listOfRedisValues = getValues();
		
		
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
		redisTemplate.delete(REDISCLIENTKEY);
	}

}
