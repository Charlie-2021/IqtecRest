package com.api.iqtec.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.api.iqtec.modelo.Transporte;

public interface ITransporteService {

	public List<Transporte> findAll ();
	
	public boolean insert(Transporte transporte);

	public boolean update(Transporte transporte);

	public boolean delete(Long id);
	
	public Optional<Transporte> findByNombre (String nombre);
}
