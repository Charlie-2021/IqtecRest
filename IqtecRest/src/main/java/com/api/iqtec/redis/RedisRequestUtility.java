package com.api.iqtec.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Solicitud;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



@Service
public class RedisRequestUtility {

	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	Gson gson;

	
	private final String REDISREQUESTKEY = "requests";
	
	
	public void setValues(List<Solicitud> list) {
		String valuesJson = gson.toJson(list.toArray(), new TypeToken<Solicitud[]>() {}.getType());
		
		redisTemplate.opsForValue().set(REDISREQUESTKEY, valuesJson);
		redisTemplate.expire(REDISREQUESTKEY, 400, TimeUnit.MINUTES);
	}
	
	public  List<Solicitud> getValues(){
		List<Solicitud> valuesInRedis = new ArrayList<Solicitud>();
		
		Solicitud array[] = gson.fromJson(redisTemplate.opsForValue().get(REDISREQUESTKEY), new TypeToken<Solicitud[]>() {}.getType());
		
		if(array == null) {
			valuesInRedis = new ArrayList<Solicitud>();
		}else {
			valuesInRedis = new ArrayList<Solicitud>(Arrays.asList(array));

		}
		
		return valuesInRedis;
	}
	
	public void updateRedisCache(Solicitud object, String operation) {
		List<Solicitud> listOfRedisValues = getValues();
		
		
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
		redisTemplate.delete(REDISREQUESTKEY);
	}

}
