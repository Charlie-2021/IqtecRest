package com.api.iqtec.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Cliente;
import com.api.iqtec.modelo.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



@Service
public class RedisUserUtility {

	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	Gson gson;
	

    private final String REDISUSERKEY = "users";
	
	
	public void setValues(List<Usuario> list) {
		String valuesJson = gson.toJson(list.toArray(), new TypeToken<Usuario[]>() {}.getType());
		
		redisTemplate.opsForValue().set(REDISUSERKEY, valuesJson);
		redisTemplate.expire(REDISUSERKEY, 400, TimeUnit.MINUTES);
	}
	
	public  List<Usuario> getValues(){
		List<Usuario> valuesInRedis = new ArrayList<Usuario>();
		
		Usuario array[] = gson.fromJson(redisTemplate.opsForValue().get(REDISUSERKEY), new TypeToken<Usuario[]>() {}.getType());
		
		if(array == null) {
			valuesInRedis = new ArrayList<Usuario>();
		}else {
			valuesInRedis = new ArrayList<Usuario>(Arrays.asList(array));

		}
		
		return valuesInRedis;
	}
	
	public void updateRedisCache(Usuario object, String operation) {
		List<Usuario> listOfRedisValues = getValues();
		
		
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
		redisTemplate.delete(REDISUSERKEY);
	}

}
