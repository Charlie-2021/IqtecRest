package com.api.iqtec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Sede;
import com.api.iqtec.redis.RedisHeadquaterUtility;
import com.api.iqtec.repositorio.SedeRepository;
import com.api.iqtec.service.interfaces.ISedeService;

@Service
public class SedeService implements ISedeService {

	@Autowired SedeRepository daoSede;
	
	@Autowired RedisHeadquaterUtility redisHeadquaterUtility;
	
	
	
	@Override
	public List<Sede> findAll() {
		
		List<Sede> headquaters = redisHeadquaterUtility.getValues();
		
		if(headquaters.size() < 1) {
			headquaters = (List<Sede>) daoSede.findAll();
			
			redisHeadquaterUtility.setValues(headquaters);
		}
		
		return headquaters;
	}

	@Override
	public boolean insert(Sede sede) {
		
		boolean exito = false;
		
		if(sede.getIdSede() == null || !daoSede.existsById(sede.getIdSede()))
		{
			daoSede.save(sede);
			
			redisHeadquaterUtility.updateRedisCache(daoSede.findByNombre(sede.getNombre()).get(), "insert");
			
			exito = true;
		}
		return exito;
	}

	@Override
	public boolean update(Sede sede) {
		
		boolean exito = false;
		
		if(daoSede.existsById(sede.getIdSede()))
		{
			daoSede.save(sede);
			
			redisHeadquaterUtility.updateRedisCache(sede, "update");
			
			exito = true;
		}
		return exito;
	}

	@Override
	public boolean delete(Long id) {
		
		boolean exito = false;

		if(daoSede.existsById(id))
		{
			redisHeadquaterUtility.updateRedisCache(daoSede.findById(id).get(), "delete");

			daoSede.deleteById(id);
			exito = true;
		}
		return exito;
	}

	@Override
	public Optional<Sede> findByNombre(String nombre) {
		// TODO Auto-generated method stub
		return daoSede.findByNombre(nombre);
	}

}
