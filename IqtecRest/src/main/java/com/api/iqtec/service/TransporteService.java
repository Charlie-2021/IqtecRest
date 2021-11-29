package com.api.iqtec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Transporte;
import com.api.iqtec.redis.RedisTransportUtility;
import com.api.iqtec.repositorio.TransporteRepository;
import com.api.iqtec.service.interfaces.ITransporteService;

@Service
public class TransporteService implements ITransporteService {

	@Autowired TransporteRepository daoTransporte;
	

	@Autowired RedisTransportUtility redisUtility;
	
	
	
	@Override
	public List<Transporte> findAll() {

		List<Transporte> transports = redisUtility.getValues();
		
		if(transports.size() < 1) {
			transports = (List<Transporte>) daoTransporte.findAll();
			
			redisUtility.setValues(transports);
		}
		
		return transports;
	}

	@Override
	public boolean insert(Transporte transporte) {
		
		boolean exito = false;
		
		if(transporte.getId() == null ||!daoTransporte.existsById(transporte.getId()))
		{
			daoTransporte.save(transporte);
			
			redisUtility.updateRedisCache(daoTransporte.findByNombre(transporte.getNombre()).get(), "insert");
			
			exito = true;
		}
		return exito;
	}

	@Override
	public boolean update(Transporte transporte) {
		
		boolean exito = false;
		
		if(daoTransporte.existsById(transporte.getId()))
		{
			daoTransporte.save(transporte);
			
			redisUtility.updateRedisCache(transporte, "update");

			
			exito = true;
		}
		return exito;
	}

	@Override
	public boolean delete(Long id) {
		
		boolean exito = false;

		if(daoTransporte.existsById(id))
		{
			redisUtility.updateRedisCache(daoTransporte.findById(id).get(), "delete");

			
			daoTransporte.deleteById(id);
			exito = true;
		}
		return exito;
	}

	@Override
	public Optional<Transporte> findByNombre(String nombre) {
		// TODO Auto-generated method stub
		return daoTransporte.findByNombre(nombre);
	}

}
