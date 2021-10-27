package com.api.iqtec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Proyecto;
import com.api.iqtec.repositorio.ProyectoRepository;

@Service
public class ProyectoService implements IProyectoService {

	@Autowired ProyectoRepository daoProyecto;
	
	
	@Override
	public List<Proyecto> findAll() {
		// TODO Auto-generated method stub
		return (List<Proyecto>) daoProyecto.findAll();
	}

	@Override
	public boolean insert(Proyecto proyecto) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(proyecto.getIdProyecto()== null || !daoProyecto.existsById(proyecto.getIdProyecto()))
		{
			daoProyecto.save(proyecto);
			exito = true;
		}
		return exito;
	}

	@Override
	public boolean update(Proyecto proyecto) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(daoProyecto.existsById(proyecto.getIdProyecto()))
		{
			daoProyecto.save(proyecto);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(daoProyecto.existsById(id))
		{
			daoProyecto.deleteById(id);
			exito = true;
		}
		
		return exito;	
	}

}
