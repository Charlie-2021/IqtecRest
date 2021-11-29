package com.api.iqtec.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Seguimiento;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



@Service
public class RedisTrackUtility {

	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	Gson gson;

	private final String REDISTRACKINGRKEY = "track";
	
	
	public void setValues(List<Seguimiento> list) {
		String valuesJson = gson.toJson(list.toArray(), new TypeToken<Seguimiento[]>() {}.getType());
		
		redisTemplate.opsForValue().set(REDISTRACKINGRKEY, valuesJson);
		redisTemplate.expire(REDISTRACKINGRKEY, 400, TimeUnit.MINUTES);
	}
	
	public  List<Seguimiento> getValues(){
		List<Seguimiento> valuesInRedis = new ArrayList<Seguimiento>();
		
		Seguimiento array[] = gson.fromJson(redisTemplate.opsForValue().get(REDISTRACKINGRKEY), new TypeToken<Seguimiento[]>() {}.getType());
		
		if(array == null) {
			valuesInRedis = new ArrayList<Seguimiento>();
		}else {
			valuesInRedis = new ArrayList<Seguimiento>(Arrays.asList(array));

		}
		
		return valuesInRedis;
	}
	
	public void updateRedisCache(Seguimiento object, String operation) {
		List<Seguimiento> listOfRedisValues = getValues();
		
		
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
		redisTemplate.delete(REDISTRACKINGRKEY);
	}

}
