package com.api.iqtec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Seguimiento;
import com.api.iqtec.repositorio.SeguimientoRepository;
import com.api.iqtec.service.interfaces.ISeguimientoService;

@Service
public class SeguimientoService implements ISeguimientoService{

	@Autowired SeguimientoRepository daoSeguimiento;
		
	@Override
	public List<Seguimiento> findAll() {
		
		return (List<Seguimiento>) daoSeguimiento.findAll();
	}

	@Override
	public boolean update(Seguimiento seguimiento) {
		
		boolean exito = false;
		
		if(daoSeguimiento.existsById(seguimiento.getIdSeguimiento()))
		{
			daoSeguimiento.save(seguimiento);
			exito = true;
		}
		return exito;
	}

}
