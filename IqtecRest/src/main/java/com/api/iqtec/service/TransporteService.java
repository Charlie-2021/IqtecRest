package com.api.iqtec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Transporte;
import com.api.iqtec.repositorio.TransporteRepository;
import com.api.iqtec.service.interfaces.ITransporteService;

@Service
public class TransporteService implements ITransporteService {

	@Autowired TransporteRepository daoTransporte;
		
	@Override
	public List<Transporte> findAll() {

		return (List<Transporte>) daoTransporte.findAll();
	}

	@Override
	public boolean insert(Transporte transporte) {
		
		boolean exito = false;
		
		if(transporte.getId() == null ||!daoTransporte.existsById(transporte.getId()))
		{
			daoTransporte.save(transporte);
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
			exito = true;
		}
		return exito;
	}

	@Override
	public boolean delete(Long id) {
		
		boolean exito = false;

		if(daoTransporte.existsById(id))
		{
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

	@Override
	public Optional<Transporte> findById(Long id) {
		// TODO Auto-generated method stub
		return daoTransporte.findById(id);
	}

}
