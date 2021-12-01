package com.api.iqtec.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.api.iqtec.modelo.Cliente;
import com.api.iqtec.modelo.Sede;


public interface ISedeService {
	
	public List<Sede> findAll ();
	
	public boolean insert(Sede sede);

	public boolean update(Sede sede);

	public boolean delete(Long id);
	
	public Optional<Sede> findByNombre (String nombre);
	
	public Optional<Sede> findById (Long id);
}
