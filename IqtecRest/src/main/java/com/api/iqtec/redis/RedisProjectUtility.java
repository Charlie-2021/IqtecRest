package com.api.iqtec.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Proyecto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



@Service
public class RedisProjectUtility {

	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	Gson gson;
	
	private final String REDISPROJECTKEY = "projects";
	
	
	public void setValues(List<Proyecto> list) {
		String valuesJson = gson.toJson(list.toArray(), new TypeToken<Proyecto[]>() {}.getType());
		
		redisTemplate.opsForValue().set(REDISPROJECTKEY, valuesJson);
		redisTemplate.expire(REDISPROJECTKEY, 400, TimeUnit.MINUTES);
	}
	
	public  List<Proyecto> getValues(){
		List<Proyecto> valuesInRedis = new ArrayList<Proyecto>();
		
		Proyecto array[] = gson.fromJson(redisTemplate.opsForValue().get(REDISPROJECTKEY), new TypeToken<Proyecto[]>() {}.getType());
		
		if(array == null) {
			valuesInRedis = new ArrayList<Proyecto>();
		}else {
			valuesInRedis = new ArrayList<Proyecto>(Arrays.asList(array));

		}
		
		return valuesInRedis;
	}
	
	public void updateRedisCache(Proyecto object, String operation) {
		List<Proyecto> listOfRedisValues = getValues();
		
		
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
		redisTemplate.delete(REDISPROJECTKEY);
	}

}
