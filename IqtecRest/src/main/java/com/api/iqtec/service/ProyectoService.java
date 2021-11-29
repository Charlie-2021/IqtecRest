package com.api.iqtec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Proyecto;
import com.api.iqtec.redis.RedisProjectUtility;
import com.api.iqtec.repositorio.ProyectoRepository;
import com.api.iqtec.service.interfaces.IProyectoService;

@Service
public class ProyectoService implements IProyectoService {

	@Autowired ProyectoRepository daoProyecto;
	
	@Autowired RedisProjectUtility redisUtility;
	
	
	@Override
	public List<Proyecto> findAll() {
		
		List<Proyecto> projects = redisUtility.getValues();
		
		if(projects.size() < 1) {
			projects = (List<Proyecto>) daoProyecto.findAll();
			
			redisUtility.setValues(projects);
		}
		return projects;
	}

	@Override
	public boolean insert(Proyecto proyecto) {
		
		boolean exito = false;
		
		if(proyecto.getIdProyecto()== null || !daoProyecto.existsById(proyecto.getIdProyecto()))
		{
			daoProyecto.save(proyecto);
			
			redisUtility.updateRedisCache(daoProyecto.findByNombre(proyecto.getNombre()).get(), "insert");
			
			exito = true;
		}
		return exito;
	}

	@Override
	public boolean update(Proyecto proyecto) {
		
		boolean exito = false;
		
		if(daoProyecto.existsById(proyecto.getIdProyecto()))
		{
			daoProyecto.save(proyecto);
			
			redisUtility.updateRedisCache(proyecto, "update");

			
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean delete(Long id) {
		
		boolean exito = false;
		
		if(daoProyecto.existsById(id))
		{
			
			redisUtility.updateRedisCache(daoProyecto.findById(id).get(), "delete");
			
			daoProyecto.deleteById(id);
			exito = true;
		}
		
		return exito;	
	}

}
