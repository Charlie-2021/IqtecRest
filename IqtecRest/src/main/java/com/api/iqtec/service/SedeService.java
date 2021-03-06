package com.api.iqtec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Sede;
import com.api.iqtec.repositorio.SedeRepository;
import com.api.iqtec.service.interfaces.ISedeService;

@Service
public class SedeService implements ISedeService {

	@Autowired SedeRepository daoSede;
	
	@Override
	public List<Sede> findAll() {

		return (List<Sede>) daoSede.findAll();
	}

	@Override
	public boolean insert(Sede sede) {
		
		boolean exito = false;
		
		if(sede.getIdSede() == null || !daoSede.existsById(sede.getIdSede()))
		{
			daoSede.save(sede);
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
			exito = true;
		}
		return exito;
	}

	@Override
	public boolean delete(Long id) {
		
		boolean exito = false;

		if(daoSede.existsById(id))
		{
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

	@Override
	public Optional<Sede> findById(Long id) {
		// TODO Auto-generated method stub
		return daoSede.findById(id);
	}

}
