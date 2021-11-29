package com.api.iqtec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Seguimiento;
import com.api.iqtec.redis.RedisTrackUtility;
import com.api.iqtec.repositorio.SeguimientoRepository;
import com.api.iqtec.service.interfaces.ISeguimientoService;

@Service
public class SeguimientoService implements ISeguimientoService{

	@Autowired SeguimientoRepository daoSeguimiento;
	
	@Autowired RedisTrackUtility redisUtility;
	
	
	@Override
	public List<Seguimiento> findAll() {
		
		List<Seguimiento> tracking = redisUtility.getValues();
		
		if(tracking.size() < 1) {
			tracking = (List<Seguimiento>) daoSeguimiento.findAll();
			
			redisUtility.setValues(tracking);
		}
		
		return tracking;
	}

	@Override
	public boolean update(Seguimiento seguimiento) {
		
		boolean exito = false;
		
		if(daoSeguimiento.existsById(seguimiento.getIdSeguimiento()))
		{
			daoSeguimiento.save(seguimiento);
			
			redisUtility.updateRedisCache(seguimiento, "update");
			
			exito = true;
		}
		return exito;
	}

}
