package com.api.iqtec.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.api.iqtec.modelo.Cliente;
import com.api.iqtec.modelo.Proyecto;

public interface IProyectoService {

	public List<Proyecto> findAll ();
	
	public boolean insert(Proyecto proyecto);

	public boolean update(Proyecto proyecto);

	public boolean delete(Long id);
	
	public Optional<Proyecto> findById (Long id);
}
